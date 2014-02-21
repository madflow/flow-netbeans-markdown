package flow.netbeans.markdown.utils;

import flow.netbeans.markdown.highlighter.MarkdownTokenId;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.openide.text.NbDocument;
import org.openide.util.Exceptions;

/**
 *
 * @author junichi11
 */
public final class MarkdownDocUtil {

    private MarkdownDocUtil() {
    }

    /**
     * Get TokenSequence.
     *
     * @param document
     * @return TokenSequence
     */
    public static TokenSequence<MarkdownTokenId> getTokenSequence(Document document) {
        TokenHierarchy<Document> tokenHierarchy = TokenHierarchy.get(document);
        AbstractDocument ad = (AbstractDocument) document;
        ad.readLock();
        TokenSequence<MarkdownTokenId> ts;
        try {
            ts = tokenHierarchy.tokenSequence(MarkdownTokenId.language());
        } finally {
            ad.readUnlock();
        }
        return ts;
    }

    /**
     * Get indent string for caret offset position.
     *
     * @param document document
     * @param caretOffset caret offset
     * @return indent string if indent exists, empty string otherwise
     */
    public static String getIndentString(Document document, int caretOffset) {
        String indentString = ""; // NOI18N
        if (document instanceof StyledDocument) {
            StyledDocument sd = (StyledDocument) document;
            int lineNumber = NbDocument.findLineNumber(sd, caretOffset);
            int lineOffset = NbDocument.findLineOffset(sd, lineNumber);
            try {
                String currentLine = document.getText(lineOffset, caretOffset - lineOffset);
                for (int i = 0; i < currentLine.length(); i++) {
                    char ch = currentLine.charAt(i);
                    if (ch != ' ' && ch != '\t') { // NOI18N
                        break;
                    }
                    indentString = indentString + ch;
                }
            } catch (BadLocationException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        return indentString;
    }
}
