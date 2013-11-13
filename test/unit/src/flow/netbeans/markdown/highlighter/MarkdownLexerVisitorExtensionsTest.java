
package flow.netbeans.markdown.highlighter;

import java.io.IOException;
import org.junit.Test;
import org.pegdown.Extensions;

/**
 * Unit tests for {@link MarkdownLexerVisitor} when used with the PegDown extensions to standard Markdown.
 * At the moment only basic properties of the generated token sequence are checked:
 * <ul>
 * <li>All tokens have non-zero length.</li>
 * <li>The total length of all tokens is equal to the length of the input.</li>
 * </ul>
 */
public class MarkdownLexerVisitorExtensionsTest extends MarkdownLexerVisitorTestSupport {
    @Test
    public void testAbbreviationsEnabled() throws IOException {
        runTestWithExtensions("extensions/abbreviations", Extensions.ABBREVIATIONS);
    }

    @Test
    public void testAbbreviationsDisabled() throws IOException {
        runTestWithExtensions("extensions/abbreviations", 0);
    }

    @Test
    public void testAutolinksEnabled() throws IOException {
        runTestWithExtensions("extensions/autolinks", Extensions.AUTOLINKS);
    }

    @Test
    public void testAutolinksDisabled() throws IOException {
        runTestWithExtensions("extensions/autolinks", 0);
    }

    @Test
    public void testDefinitionsEnabled() throws IOException {
        runTestWithExtensions("extensions/definitions", Extensions.DEFINITIONS);
    }

    @Test
    public void testDefinitionsDisabled() throws IOException {
        runTestWithExtensions("extensions/definitions", 0);
    }

    @Test
    public void testFencedCodeBlocksEnabled() throws IOException {
        runTestWithExtensions("extensions/fenced_code_blocks", Extensions.FENCED_CODE_BLOCKS);
    }

    @Test
    public void testFencedCodeBlocksDisabled() throws IOException {
        runTestWithExtensions("extensions/fenced_code_blocks", 0);
    }

    @Test
    public void testHardwrapsEnabled() throws IOException {
        runTestWithExtensions("extensions/hardwraps", Extensions.HARDWRAPS);
    }

    @Test
    public void testHardwrapsDisabled() throws IOException {
        runTestWithExtensions("extensions/hardwraps", 0);
    }

    @Test
    public void testQuotesEnabled() throws IOException {
        runTestWithExtensions("extensions/quotes", Extensions.QUOTES);
    }

    @Test
    public void testQuotesDisabled() throws IOException {
        runTestWithExtensions("extensions/quotes", 0);
    }

    @Test
    public void testSmartsEnabled() throws IOException {
        runTestWithExtensions("extensions/smarts", Extensions.SMARTS);
    }

    @Test
    public void testSmartsDisabled() throws IOException {
        runTestWithExtensions("extensions/smarts", 0);
    }

    @Test
    public void testTablesEnabled() throws IOException {
        runTestWithExtensions("extensions/tables", Extensions.TABLES);
    }

    @Test
    public void testTablesDisabled() throws IOException {
        runTestWithExtensions("extensions/tables", 0);
    }

    @Test
    public void testWikilinksEnabled() throws IOException {
        runTestWithExtensions("extensions/wikilinks", Extensions.WIKILINKS);
    }

    @Test
    public void testWikilinksDisabled() throws IOException {
        runTestWithExtensions("extensions/wikilinks", 0);
    }

    @Test
    public void testSuppressHtmlBlocksEnabled() throws IOException {
        runTestWithExtensions("extensions/suppress_html_blocks", Extensions.SUPPRESS_HTML_BLOCKS);
    }

    @Test
    public void testSuppressHtmlBlocksDisabled() throws IOException {
        runTestWithExtensions("extensions/suppress_html_blocks", 0);
    }

    @Test
    public void testSuppressInlineHtmlEnabled() throws IOException {
        runTestWithExtensions("extensions/suppress_inline_html", Extensions.SUPPRESS_INLINE_HTML);
    }

    @Test
    public void testSuppressInlineHtmlDisabled() throws IOException {
        runTestWithExtensions("extensions/suppress_inline_html", 0);
    }
}
