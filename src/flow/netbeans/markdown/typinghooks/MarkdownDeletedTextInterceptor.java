package flow.netbeans.markdown.typinghooks;

import flow.netbeans.markdown.csl.MarkdownLanguageConfig;
import flow.netbeans.markdown.highlighter.MarkdownTokenId;
import flow.netbeans.markdown.options.MarkdownGlobalOptions;
import flow.netbeans.markdown.utils.MarkdownDocUtil;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.netbeans.api.editor.mimelookup.MimePath;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.spi.editor.typinghooks.DeletedTextInterceptor;

/**
 *
 * @author junichi11
 */
public class MarkdownDeletedTextInterceptor implements DeletedTextInterceptor {

    @Override
    public boolean beforeRemove(Context context) throws BadLocationException {
        if (!isEnabled()) {
            return false;
        }

        char ch = context.getText().charAt(0);
        if (ch != '.' && ch != ' ') { // NOI18N
            return false;
        }
        Document document = context.getDocument();
        int caretOffset = context.getOffset();
        TokenSequence<MarkdownTokenId> ts = MarkdownDocUtil.getTokenSequence(document);
        ts.move(caretOffset);
        if (ch == ' ') { // NOI18N
            ts.movePrevious();
            Token<MarkdownTokenId> token = ts.token();
            if (caretOffset != ts.offset() + token.length()) {
                return false;
            }
        } else {
            ts.moveNext();
        }

        Token<MarkdownTokenId> token = ts.token();
        if (token == null || token.id() != MarkdownTokenId.ORDEREDLIST) {
            return false;
        }

        String tokenText = token.text().toString();
        // may text is ' '
        if (tokenText.matches("\\d+\\.")) { // NOI18N
            return false;
        }

        // compute offset and length for removing
        int removeStartOffset = ts.offset();
        int removeLength = tokenText.length();
        if (ts.movePrevious()) {
            Token<MarkdownTokenId> previousToken = ts.token();
            if (previousToken != null) {
                String previousText = previousToken.text().toString();
                int lastIndexOfNewLine = previousText.lastIndexOf('\n'); // NOI18N
                if (lastIndexOfNewLine != -1) {
                    removeStartOffset = ts.offset() + lastIndexOfNewLine;
                    int indent = previousToken.length() - lastIndexOfNewLine;
                    removeLength = removeLength + indent;
                }
            }
        }

        // reorder
        OrderedListReorderer reorderer = new OrderedListReorderer(context.getComponent(), document, caretOffset);
        reorderer.reorder(false, removeStartOffset, removeLength);
        return true;
    }

    @Override
    public void remove(Context context) throws BadLocationException {
    }

    @Override
    public void afterRemove(Context context) throws BadLocationException {
    }

    @Override
    public void cancelled(Context context) {
    }

    private boolean isEnabled() {
        return MarkdownGlobalOptions.getInstance().isTypingHooks();
    }

    @MimeRegistration(mimeType = MarkdownLanguageConfig.MIME_TYPE, service = DeletedTextInterceptor.Factory.class)
    public static class Factory implements DeletedTextInterceptor.Factory {

        @Override
        public DeletedTextInterceptor createDeletedTextInterceptor(MimePath mimePath) {
            return new MarkdownDeletedTextInterceptor();
        }
    }
}
