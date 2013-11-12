package flow.netbeans.markdown.highlighter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import org.pegdown.ast.Node;

/**
 *
 * @author Holger
 */
public class MarkdownTokenListBuilder {
    private final Deque<MarkdownLexerVisitor.MarkdownTreeToken> stack;

    private final List<MarkdownToken> tokens;

    private int depth;

    public MarkdownTokenListBuilder(int inputLength) {
        this.stack = new ArrayDeque<MarkdownLexerVisitor.MarkdownTreeToken>();
        this.tokens = new ArrayList<MarkdownToken>();
        this.depth = 0;
        // Push guard tree token.
        this.stack.push(new MarkdownLexerVisitor.MarkdownTreeToken(MarkdownTokenId.WHITESPACE, this.depth, 0,
                inputLength));
    }

    public void addLeafTreeToken(MarkdownTokenId id, Node node) {
        beginTreeToken(id, node);
        endTreeToken();
    }

    public void beginTreeToken(MarkdownTokenId id, Node node) {
        depth++;
        MarkdownLexerVisitor.MarkdownTreeToken topTreeToken = stack.peek();
        final int startIndex = Math.max(node.getStartIndex(), topTreeToken.getStartIndex());
        final int endIndex = Math.min(node.getEndIndex(), topTreeToken.getEndIndex());
        addToken(topTreeToken.getId(), topTreeToken.getRemainderIndex(), startIndex);
        topTreeToken.setRemainderIndex(endIndex);
        MarkdownLexerVisitor.MarkdownTreeToken treeToken = new MarkdownLexerVisitor.MarkdownTreeToken(id, depth,
                startIndex, endIndex);
//        LOG.log(Level.INFO, "Added tree token to stack: {0}", treeToken.toString());
        stack.push(treeToken);
    }

    public void endTreeToken() {
        MarkdownLexerVisitor.MarkdownTreeToken treeToken = stack.pop();
//        LOG.log(Level.INFO, "Removed tree token from stack: {0}", treeToken.toString());
        addToken(treeToken.getId(), treeToken.getRemainderIndex(), treeToken.getEndIndex());
        depth--;
    }

    private void addToken(MarkdownTokenId id, int startIndex, int endIndex) {
        if (startIndex < endIndex) {
            MarkdownToken token = new MarkdownToken(id, startIndex, endIndex);
//            LOG.log(Level.INFO, "Added token to list: {0}", token);
            tokens.add(token);
        }
    }

    public List<MarkdownToken> build() {
        if (stack.size() == 1) {
            // Pop guard tree token.
            endTreeToken();
        }
        return Collections.unmodifiableList(tokens);
    }
}
