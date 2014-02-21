package flow.netbeans.markdown.utils;

import flow.netbeans.markdown.highlighter.MarkdownTokenId;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import org.junit.Test;
import org.netbeans.api.lexer.Language;
import org.netbeans.junit.NbTestCase;

/**
 *
 * @author junichi11
 */
public class MarkdownDocUtilTest extends NbTestCase {

    public MarkdownDocUtilTest(String name) {
        super(name);
    }

    /**
     * Test of getIndentString method, of class MarkdownDocUtil.
     *
     * @throws javax.swing.text.BadLocationException
     */
    @Test
    public void testGetIndentString() throws BadLocationException {
        DefaultStyledDocument document = new DefaultStyledDocument();
        document.putProperty(Language.class, MarkdownTokenId.language());
        document.insertString(0, "    * list\n", null); // white space
        String result = MarkdownDocUtil.getIndentString(document, 10);
        assertEquals("    ", result);

        document.insertString(0, "	* list\n", null); // tab space
        result = MarkdownDocUtil.getIndentString(document, 7);
        assertEquals("	", result);

        document.insertString(0, "  	  * list\n", null); // white space and tab space
        result = MarkdownDocUtil.getIndentString(document, 11);
        assertEquals("  	  ", result);

        document.insertString(0, "* list\n", null); // no indent
        result = MarkdownDocUtil.getIndentString(document, 6);
        assertEquals("", result);
    }

}
