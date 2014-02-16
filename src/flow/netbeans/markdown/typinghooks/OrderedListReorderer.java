package flow.netbeans.markdown.typinghooks;

import flow.netbeans.markdown.highlighter.MarkdownTokenId;
import flow.netbeans.markdown.utils.MarkdownDocUtil;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenSequence;
import org.openide.text.NbDocument;
import org.openide.util.Exceptions;

/**
 *
 * @author junichi11
 */
public class OrderedListReorderer {

    private final JTextComponent component;
    private final Document document;
    private final int caretOffset;

    public OrderedListReorderer(JTextComponent component, Document document, int caretOffset) {
        this.component = component;
        this.document = document;
        this.caretOffset = caretOffset;
    }

    /**
     * Reorder odered list.
     *
     * @param isInserted {@code true} if break line, {@code false} otherwise.
     */
    public void reorder(final boolean isInserted) {
        reorder(isInserted, -1, 0);
    }

    /**
     * Reorder odered list.
     *
     * @param isInserted {@code true} if break line, {@code false} otherwise.
     * @param removeOffset offset for removing
     * @param removeLength length for removing
     */
    public void reorder(final boolean isInserted, final int removeOffset, final int removeLength) {
        final Map<Integer, Integer> orderedListMap = getOrderedListMap(isInserted);

        // sort
        final LinkedList<Integer> keyList = new LinkedList<Integer>(orderedListMap.keySet());
        Collections.sort(keyList);
        Collections.reverse(keyList);

        // reorder
        NbDocument.runAtomic((StyledDocument) document, new Runnable() {
            @Override
            public void run() {
                for (Integer offset : keyList) {
                    try {
                        Integer number = orderedListMap.get(offset);
                        Integer oldNumber;
                        if (isInserted) {
                            oldNumber = number - 1;
                        } else {
                            oldNumber = number + 1;
                        }
                        int removeLength = oldNumber.toString().length();
                        document.remove(offset, removeLength);
                        document.insertString(offset, number.toString(), null);
                    } catch (BadLocationException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }

                // remove
                if (removeOffset >= 0 && removeLength > 0) {
                    try {
                        document.remove(removeOffset, removeLength);
                    } catch (BadLocationException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            }
        });
    }

    private Map<Integer, Integer> getOrderedListMap(boolean isInserted) {
        // get token sequence
        TokenSequence<MarkdownTokenId> ts = MarkdownDocUtil.getTokenSequence(document);
        if (ts == null) {
            return Collections.emptyMap();
        }

        // reorder ordered list number
        ts.move(caretOffset);
        final HashMap<Integer, Integer> orderedListMap = new HashMap<Integer, Integer>();
        boolean isFirst = true;
        int nextNumber = 0;
        while (ts.moveNext()) {
            Token<MarkdownTokenId> token = ts.token();
            MarkdownTokenId id = token.id();
            if (id != MarkdownTokenId.ORDEREDLIST) {
                continue;
            }

            int offset = ts.offset();
            String numberText = token.text().toString();
            if (numberText.matches("^\n+$")) { // NOI18N
                break;
            }

            if (numberText.startsWith("\n")) { // NOI18N
                numberText = numberText.replace("\n", ""); // NOI18N
                offset++;
            }
            int dotIndex = numberText.indexOf("."); // NOI18N
            if (dotIndex == -1) {
                continue;
            }
            numberText = numberText.substring(0, dotIndex);

            int currentNumber;
            try {
                currentNumber = Integer.parseInt(numberText);
            } catch (NumberFormatException e) {
                continue;
            }

            if (isInserted) {
                if (isFirst) {
                    nextNumber = currentNumber;
                    isFirst = false;
                    continue;
                }
                if (currentNumber == nextNumber) {
                    nextNumber++;
                    orderedListMap.put(offset, nextNumber);
                    continue;
                }
            } else {
                if (isFirst) {
                    if (currentNumber == 1) {
                        break;
                    }
                    nextNumber = currentNumber;
                    isFirst = false;
                }
                if (nextNumber == currentNumber) {
                    nextNumber = currentNumber + 1;
                    orderedListMap.put(offset, --currentNumber);
                    continue;
                }
            }
            // not consecutive number
            break;
        }
        return orderedListMap;
    }
}
