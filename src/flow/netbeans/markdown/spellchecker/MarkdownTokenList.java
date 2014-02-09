package flow.netbeans.markdown.spellchecker;

import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.netbeans.lib.editor.util.swing.DocumentUtilities;
import org.netbeans.modules.spellchecker.spi.language.TokenList;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;

/**
 *
 * @author Holger Stenger
 */
public class MarkdownTokenList implements TokenList {
    private final Document doc;

    private CharSequence currentWordText;

    private int currentWordStartOffset;

    private boolean hidden;
    private int nextSearchOffset;

    MarkdownTokenList(Document doc) {
        this.doc = doc;
        this.hidden = false;
    }

    @Override
    public void setStartOffset(final int startOffset) {
        currentWordText = null;
        currentWordStartOffset = -1;

        CharSequence content = DocumentUtilities.getText(doc);

        nextSearchOffset = findCurrentWordStart(content, startOffset);

        FileObject fileObject = FileUtil.getConfigFile("Spellcheckers/Markdown"); //NOI18N
        Boolean b = (Boolean) fileObject.getAttribute("Hidden");//NOI18N
        hidden = Boolean.TRUE.equals(b);
    }

    @Override
    public boolean nextWord() {
        if (hidden) {
            return false;
        }

        try {
            CharSequence content = DocumentUtilities.getText(doc);

            final int startOffset = findNextWordStart(content, nextSearchOffset);
            final int endOffset = findCurrentWordEnd(content, startOffset);

            if (startOffset < endOffset) {
                currentWordStartOffset = startOffset;
                nextSearchOffset = endOffset;
                currentWordText = doc.getText(startOffset, endOffset - startOffset);
                return true;
            } else {
                nextSearchOffset = content.length();
                return false;
            }
        }
        catch (BadLocationException ex) {
            Exceptions.printStackTrace(ex);
            return false;
        }
    }

    /**
     * Finds the index of the first letter character at or after the given index.
     * @param content The character sequence to search.
     * @param fromIndex The index to begin searching.
     * @return The index of a letter character of the length of the character sequence 
     * if no letter character was found.
     */
    private int findNextWordStart(CharSequence content, int fromIndex) {
        return findNextWordBoundary(content, fromIndex, false);
    }

    /**
     * Finds the index of the first non-letter character at or after the given index.
     * @param content The character sequence to search.
     * @param fromIndex The index to begin searching.
     * @return The index of a non-letter character of the length of the character 
     * sequence if no non-letter character was found.
     */
    private int findCurrentWordEnd(CharSequence content, int fromIndex) {
        return findNextWordBoundary(content, fromIndex, true);
    }

    /**
     * Finds the index of the first letter of the word which contains the given index.
     * @param content The character sequence to search.
     * @param fromIndex The index to begin searching.
     * @return The index of the first letter character which is preceded by a non-letter 
     * character or the start of the character sequence if the given index points 
     * to a letter character. The given index if it points to a non-letter character 
     * or is outside the range of valid indices.
     */
    private int findCurrentWordStart(CharSequence content, int fromIndex) {
        int index = fromIndex;
        while ((index > 0) && (index < content.length()) && Character.isLetter(content.charAt(index - 1))) {
            index--;
        }
        return index;
    }

    private int findNextWordBoundary(CharSequence content, int fromIndex, boolean findEnd) {
        int index = fromIndex;
        while ((index < content.length()) && (findEnd == Character.isLetter(content.charAt(index)))) {
            index++;
        }
        return index;
    }

    @Override
    public int getCurrentWordStartOffset() {
        return currentWordStartOffset;
    }

    @Override
    public CharSequence getCurrentWordText() {
        return currentWordText;
    }

    @Override
    public void addChangeListener(ChangeListener cl) {
    }

    @Override
    public void removeChangeListener(ChangeListener cl) {
    }
}
