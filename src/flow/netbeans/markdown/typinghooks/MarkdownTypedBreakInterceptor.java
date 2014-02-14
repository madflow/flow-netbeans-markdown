package flow.netbeans.markdown.typinghooks;

import flow.netbeans.markdown.csl.MarkdownLanguageConfig;
import flow.netbeans.markdown.highlighter.MarkdownTokenId;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import org.netbeans.api.editor.mimelookup.MimePath;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.spi.editor.typinghooks.TypedBreakInterceptor;
import org.openide.text.NbDocument;
import org.openide.util.Exceptions;

/**
 *
 * @author junichi11
 */
public class MarkdownTypedBreakInterceptor implements TypedBreakInterceptor {

    private static final String INSERT_REGEX = "^((?<indent>\t+|\\s{4,})*((?<hl>[-*+>] |(?<number>\\d+)\\. ).+|.*))$"; // NOI18N
    private static final String AFTER_INSERT_REGEX = "^(?<indent>\t+|\\s{4,})*(?<hl>[-*+>] |(?<number>\\s\\d+)\\. )$"; // NOI18N
    private static final String HEADER_LIST_GROUP = "hl"; // NOI18N
    private static final String NUMBER_GROUP = "number"; // NOI18N
    private static final String INDENT_GROUP = "indent"; // NOI18N
    private static final Pattern INSERT_PATTERN = Pattern.compile(INSERT_REGEX);
    private static final Pattern AFTER_INSERT_PATTERN = Pattern.compile(AFTER_INSERT_REGEX);

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

        StringBuilder sb = new StringBuilder("\n"); // NOI18N
        Matcher matcher = INSERT_PATTERN.matcher(lineText);
        if (matcher.find()) {
            String numberText = matcher.group(NUMBER_GROUP);
            String hlText = matcher.group(HEADER_LIST_GROUP);
            String indentText = matcher.group(INDENT_GROUP);
            if (indentText != null) {
                sb.append(indentText);
            }

            if (numberText != null) {
                int number = Integer.parseInt(numberText);
                sb.append(++number).append(". "); // NOI18N
            } else if (hlText != null) {
                sb.append(hlText);
            }
        }
        context.setText(sb.toString(), 0, sb.length());
    }

    @Override
    public void afterInsert(Context context) throws BadLocationException {
        final StyledDocument document = (StyledDocument) context.getDocument();
        int caretOffset = context.getCaretOffset();
        int lineNumber = NbDocument.findLineNumber(document, caretOffset);
        int lineOffset = NbDocument.findLineOffset(document, lineNumber);
        String lineText = document.getText(lineOffset, caretOffset - lineOffset);

        // remove empty list
        // e.g. If user presses enter key when caret position is after "- " (i.e. "- [here]"), "- " is removed.
        Matcher matcher = AFTER_INSERT_PATTERN.matcher(lineText);
        if (matcher.find()) {
            String hlText = matcher.group(HEADER_LIST_GROUP);
            if (hlText != null) {
                int length = hlText.length();
                document.remove(caretOffset - length, length);
            }
        }

        // get token sequence
        TokenSequence<MarkdownTokenId> ts = getTokenSequence(document);
        if (ts == null) {
            return;
        }

        // reorder ordered list number
        ts.move(caretOffset);
        final HashMap<Integer, Integer> orderedListMap = new HashMap<Integer, Integer>();
        ts.moveNext();
        boolean isFirst = true;
        int number = 0;
        while (ts.moveNext()) {
            Token<MarkdownTokenId> token = ts.token();
            MarkdownTokenId id = token.id();
            if (id == MarkdownTokenId.ORDEREDLIST) {
                int offset = ts.offset();
                String numberText = token.text().toString();
                if (numberText.startsWith("\n")) { // NOI18N
                    numberText = numberText.replace("\n", ""); // NOI18N
                    offset++;
                }
                int dotIndex = numberText.indexOf("."); // NOI18N
                if (dotIndex == -1) {
                    continue;
                }
                numberText = numberText.substring(0, dotIndex);
                int currentNumber = Integer.parseInt(numberText);
                if (isFirst) {
                    number = currentNumber;
                    isFirst = false;
                    continue;
                }
                if (currentNumber == number) {
                    number++;
                    orderedListMap.put(offset, number);
                    continue;
                }
                break;
            }
        }
        final LinkedList<Integer> keyList = new LinkedList<Integer>(orderedListMap.keySet());
        Collections.sort(keyList);
        Collections.reverse(keyList);
        NbDocument.runAtomic(document, new Runnable() {

            @Override
            public void run() {
                for (Integer offset : keyList) {
                    try {
                        Integer number = orderedListMap.get(offset);
                        Integer oldNumber = number - 1;
                        int removeLength = oldNumber.toString().length();
                        document.remove(offset, removeLength);
                        document.insertString(offset, number.toString(), null);
                    } catch (BadLocationException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            }
        });

    }

    @Override
    public void cancelled(Context context) {
    }

    private TokenSequence<MarkdownTokenId> getTokenSequence(Document document) {
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

    @MimeRegistration(mimeType = MarkdownLanguageConfig.MIME_TYPE, service = TypedBreakInterceptor.Factory.class)
    public static class MarkdownFactory implements TypedBreakInterceptor.Factory {

        @Override
        public TypedBreakInterceptor createTypedBreakInterceptor(MimePath mimePath) {
            return new MarkdownTypedBreakInterceptor();
        }
    }

}
