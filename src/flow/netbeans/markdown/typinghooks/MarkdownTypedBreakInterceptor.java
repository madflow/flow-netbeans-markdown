package flow.netbeans.markdown.typinghooks;

import flow.netbeans.markdown.csl.MarkdownLanguageConfig;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import org.netbeans.api.editor.mimelookup.MimePath;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.spi.editor.typinghooks.TypedBreakInterceptor;
import org.openide.text.NbDocument;

/**
 *
 * @author junichi11
 */
public class MarkdownTypedBreakInterceptor implements TypedBreakInterceptor {

    @Override
    public boolean beforeInsert(Context context) throws BadLocationException {
        return false;
    }

    @Override
    public void insert(MutableContext context) throws BadLocationException {
        StyledDocument document = (StyledDocument) context.getDocument();
        int caretOffset = context.getCaretOffset();
        int lineNumber = NbDocument.findLineNumber(document, caretOffset);
        int lineOffset = NbDocument.findLineOffset(document, lineNumber);
        String lineText = document.getText(lineOffset, caretOffset - lineOffset);

        if (lineText.matches("^\\s{4,}$")) { // NOI18N
            return;
        }

        StringBuilder sb = new StringBuilder("\n"); // NOI18N
        Pattern pattern = Pattern.compile("^([-*] |[#]+ |(\\d+)\\. |\t+|\\s{4,}).+$"); // NOI18N
        Matcher matcher = pattern.matcher(lineText);
        if (matcher.find()) {
            String numberText = matcher.group(2);
            if (numberText != null) {
                int number = Integer.parseInt(numberText);
                sb.append(++number).append(". "); // NOI18N
            } else {
                sb.append(matcher.group(1));
            }
        }
        context.setText(sb.toString(), 0, sb.length());
    }

    @Override
    public void afterInsert(Context context) throws BadLocationException {
        StyledDocument document = (StyledDocument) context.getDocument();
        int caretOffset = context.getCaretOffset();
        int lineNumber = NbDocument.findLineNumber(document, caretOffset);
        int lineOffset = NbDocument.findLineOffset(document, lineNumber);
        String lineText = document.getText(lineOffset, caretOffset - lineOffset);
        if (lineText.matches("^([-*] |[#]+ |\\d+\\. |\t+|\\s{4,})$")) { // NOI18N
            document.remove(lineOffset, lineText.length());
        }
    }

    @Override
    public void cancelled(Context context) {
    }

    @MimeRegistration(mimeType = MarkdownLanguageConfig.MIME_TYPE, service = TypedBreakInterceptor.Factory.class)
    public static class MarkdownFactory implements TypedBreakInterceptor.Factory {

        @Override
        public TypedBreakInterceptor createTypedBreakInterceptor(MimePath mimePath) {
            return new MarkdownTypedBreakInterceptor();
        }
    }

}
