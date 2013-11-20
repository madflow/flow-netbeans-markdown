
package flow.netbeans.markdown.highlighter;

import java.io.IOException;
import org.junit.Test;

/**
 * Unit tests for {@link MarkdownLexerVisitor} when used with the PegDown extensions to standard Markdown.
 * At the moment only basic properties of the generated token sequence are checked:
 * <ul>
 * <li>All tokens have non-zero length.</li>
 * <li>The total length of all tokens is equal to the length of the input.</li>
 * </ul>
 */
public class MarkdownLexerVisitorBasicsTest extends MarkdownLexerVisitorTestSupport {
    @Test
    public void testHeaders() throws IOException {
        runTestWithExtensions("basics/header", 0);
    }
    @Test
    public void testQuotes() throws IOException {
        runTestWithExtensions("basics/quote", 0);
    }

    @Test
    public void testOrderedLists() throws IOException {
        runTestWithExtensions("basics/list_ordered", 0);
    }

    @Test
    public void testUnorderedLists() throws IOException {
        runTestWithExtensions("basics/list_unordered", 0);
    }

    @Test
    public void testHorizontalRules() throws IOException {
        runTestWithExtensions("basics/horizontal_rule", 0);
    }

    @Test
    public void testEmphasis() throws IOException {
        runTestWithExtensions("basics/emphasis", 0);
    }

    @Test
    public void testAutoLinks() throws IOException {
        runTestWithExtensions("basics/link_auto", 0);
    }

    @Test
    public void testInlineLinks() throws IOException {
        runTestWithExtensions("basics/link_inline", 0);
    }

    @Test
    public void testReferenceLinks() throws IOException {
        runTestWithExtensions("basics/link_reference", 0);
    }

    @Test
    public void testInlineImages() throws IOException {
        runTestWithExtensions("basics/image_inline", 0);
    }

    @Test
    public void testReferenceImages() throws IOException {
        runTestWithExtensions("basics/image_reference", 0);
    }

}
