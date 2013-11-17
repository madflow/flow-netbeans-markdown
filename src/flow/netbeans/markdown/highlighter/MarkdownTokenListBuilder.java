package flow.netbeans.markdown.highlighter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * The {@link MarkdownTokenListBuilder} is used to build a list of tokens during a depth-first left-to-right traversal
 * of a PegDown AST. This functionality was extracted from the {@link MarkdownLexerVisitor} to simplify unit testing.
 * @author Holger Stenger
 */
public class MarkdownTokenListBuilder {
    private final Deque<MarkdownLexerVisitor.MarkdownTreeToken> stack;

    private final List<MarkdownToken> tokens;

    /**
     * Creates a {@link MarkdownTokenListBuilder}. The total length of the tokens created by the builder will be equal
     * to the given total length. If the first added token does not start at position {@code 0}, the builder will insert
     * a synthetic token to fill the gap. Likewise, if the last added token does not end at position
     * {@code totalLength}, the builder will insert a synthetic token to fill the gap.
     * @param totalLength The total length of the created tokens.
     */
    public MarkdownTokenListBuilder(int totalLength) {
        stack = new ArrayDeque<MarkdownLexerVisitor.MarkdownTreeToken>();
        tokens = new ArrayList<MarkdownToken>();
        // Push guard tree token.
        stack.push(new MarkdownLexerVisitor.MarkdownTreeToken(MarkdownTokenId.PLAIN, stack.size(), 0,
                totalLength));
    }

    /**
     * Add a token which contains no nested tokens. This method is equivalent to a call to
     * {@link #beginTreeToken(flow.netbeans.markdown.highlighter.MarkdownTokenId, int, int)} directly followed by a call
     * to {@link #endTreeToken()}.
     * @param id The token id.
     * @param startIndex The start index of the token (inclusive).
     * @param endIndex The end index of the token (exclusive).
     */
    public void addLeafTreeToken(MarkdownTokenId id, int startIndex, int endIndex) {
        beginTreeToken(id, startIndex, endIndex);
        endTreeToken();
    }

    /**
     * Begins a token which can contain nested tokens. The call to this method has to be matched with a call to
     * {@link #endTreeToken()}.
     * @param id The token id.
     * @param startIndex The start index of the token (inclusive).
     * @param endIndex The end index of the token (exclusive).
     */
    public void beginTreeToken(final MarkdownTokenId id, final int startIndex, final int endIndex) {
        if (stack.isEmpty()) {
            throw new IllegalStateException("No tokens can be added after calling build");
        }
        MarkdownLexerVisitor.MarkdownTreeToken topTreeToken = stack.peek();
        final int effectiveStartIndex = Math.max(startIndex, topTreeToken.getStartIndex());
        final int effectiveEndIndex = Math.min(endIndex, topTreeToken.getEndIndex());
        addToken(topTreeToken.getId(), topTreeToken.getRemainderIndex(), effectiveStartIndex);
        topTreeToken.setRemainderIndex(effectiveEndIndex);
        MarkdownLexerVisitor.MarkdownTreeToken treeToken = new MarkdownLexerVisitor.MarkdownTreeToken(id, stack.size(),
                effectiveStartIndex, effectiveEndIndex);
//        LOG.log(Level.INFO, "Added tree token to stack: {0}", treeToken.toString());
        stack.push(treeToken);
    }

    /**
     * Ends a token which has been begun by a call to
     * {@link #beginTreeToken(flow.netbeans.markdown.highlighter.MarkdownTokenId, int, int)}.
     */
    public void endTreeToken() {
        if (stack.size() < 2) {
            // Protect guard tree token.
            throw new IllegalStateException("No token on stack");
        }
        endTreeTokenUnchecked();
    }

    private void endTreeTokenUnchecked() {
        MarkdownLexerVisitor.MarkdownTreeToken treeToken = stack.pop();
//        LOG.log(Level.INFO, "Removed tree token from stack: {0}", treeToken.toString());
        addToken(treeToken.getId(), treeToken.getRemainderIndex(), treeToken.getEndIndex());
    }

    private void addToken(MarkdownTokenId id, int startIndex, int endIndex) {
        if (startIndex < endIndex) {
            MarkdownToken token = new MarkdownToken(id, startIndex, endIndex);
//            LOG.log(Level.INFO, "Added token to list: {0}", token);
            tokens.add(token);
        }
    }

    /**
     * Finishes the token list construction.
     * @return The list of created tokens.
     */
    public List<MarkdownToken> build() {
        if (stack.size() > 1) {
            throw new IllegalStateException("Still tokens on stack");
        }
        if (stack.size() == 1) {
            // Pop guard tree token.
            endTreeTokenUnchecked();
        }
        return Collections.unmodifiableList(tokens);
    }
}
