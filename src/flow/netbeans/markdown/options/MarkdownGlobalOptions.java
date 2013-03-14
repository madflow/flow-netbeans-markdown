package flow.netbeans.markdown.options;

import java.util.prefs.Preferences;
import org.pegdown.Extensions;

/**
 * @see https://github.com/nicoulaj/idea-markdown/blob/master/src/main/java/net/nicoulaj/idea/markdown/settings/MarkdownGlobalSettings.java
 */
public final class MarkdownGlobalOptions {

    public MarkdownGlobalOptions(Preferences prefs) {
        bindPreferences(prefs);
    }

    public void bindPreferences(Preferences prefs) {
        abbreviations = prefs.getBoolean("ABBREVIATIONS", false);
        autoLinks = prefs.getBoolean("AUTOLINKS", false);
        definitions = prefs.getBoolean("DEFINITION_LISTS", false);
        fencedCodeBlocks = prefs.getBoolean("FENCED_CODE_BLOCKS", false);
        hardWraps = prefs.getBoolean("HARDWRAPS", false);
        suppressHTMLBlocks = prefs.getBoolean("HTML_BLOCK_SUPPRESSION", false);
        suppressInlineHTML = prefs.getBoolean("INLINE_HTML_SUPPRESSION", false);
        quotes = prefs.getBoolean("QUOTES", false);
        smarts = prefs.getBoolean("SMARTS", false);
        tables = prefs.getBoolean("TABLES", false);
        wikiLinks = prefs.getBoolean("WIKILINKS", false);
    }

    /**
     * Whether the "SmartyPants style pretty ellipsises, dashes and apostrophes"
     * extension should be enabled.
     */
    private boolean smarts = false;
    /**
     * Whether the "SmartyPants style pretty single and double quotes" extension
     * should be enabled.
     */
    private boolean quotes = false;
    /**
     * Whether the "PHP Markdown Extra style abbreviations" extension should be
     * enabled.
     */
    private boolean abbreviations = false;
    /**
     * Whether the "PHP Markdown Extra style definition lists" extension should
     * be enabled.
     */
    private boolean definitions = false;
    /**
     * Whether the "PHP Markdown Extra style fenced code blocks" extension
     * should be enabled.
     */
    private boolean fencedCodeBlocks = false;
    /**
     * Whether the "Github style hard wraps parsing as HTML linebreaks"
     * extension should be enabled.
     */
    private boolean hardWraps = false;
    /**
     * Whether the "Github style plain auto-links" extension should be enabled.
     */
    private boolean autoLinks = false;
    /**
     * Whether the "Wiki-style links" extension should be enabled.
     */
    private boolean wikiLinks = false;
    /**
     * Whether the "MultiMarkdown style tables support" extension should be
     * enabled.
     */
    private boolean tables = false;
    /**
     * Whether the "Suppress HTML blocks" extension should be enabled.
     */
    private boolean suppressHTMLBlocks = false;
    /**
     * Whether the "Suppress inline HTML tags" extension should be enabled.
     */
    private boolean suppressInlineHTML = false;

    /**
     * Get the extensions value to setup PegDown parser with.
     *
     * @return the value to use with {@link org.pegdown.PegDownProcessor(int)}
     */
    public int getExtensionsValue() {
        return (smarts ? Extensions.SMARTS : 0)
                + (quotes ? Extensions.QUOTES : 0)
                + (abbreviations ? Extensions.ABBREVIATIONS : 0)
                + (hardWraps ? Extensions.HARDWRAPS : 0)
                + (autoLinks ? Extensions.AUTOLINKS : 0)
                + (wikiLinks ? Extensions.WIKILINKS : 0)
                + (tables ? Extensions.TABLES : 0)
                + (definitions ? Extensions.DEFINITIONS : 0)
                + (fencedCodeBlocks ? Extensions.FENCED_CODE_BLOCKS : 0)
                + (suppressHTMLBlocks ? Extensions.SUPPRESS_HTML_BLOCKS : 0)
                + (suppressInlineHTML ? Extensions.SUPPRESS_INLINE_HTML : 0);
    }

        public boolean isSmarts() {
        return smarts;
    }

    public void setSmarts(boolean smarts) {
        this.smarts = smarts;
    }

    public boolean isQuotes() {
        return quotes;
    }

    public void setQuotes(boolean quotes) {
        this.quotes = quotes;
    }

    public boolean isAbbreviations() {
        return abbreviations;
    }

    public void setAbbreviations(boolean abbreviations) {
        this.abbreviations = abbreviations;
    }

    public boolean isDefinitions() {
        return definitions;
    }

    public void setDefinitions(boolean definitions) {
        this.definitions = definitions;
    }

    public boolean isFencedCodeBlocks() {
        return fencedCodeBlocks;
    }

    public void setFencedCodeBlocks(boolean fencedCodeBlocks) {
        this.fencedCodeBlocks = fencedCodeBlocks;
    }

    public boolean isHardWraps() {
        return hardWraps;
    }

    public void setHardWraps(boolean hardWraps) {
        this.hardWraps = hardWraps;
    }

    public boolean isAutoLinks() {
        return autoLinks;
    }

    public void setAutoLinks(boolean autoLinks) {
        this.autoLinks = autoLinks;
    }

    public boolean isWikiLinks() {
        return wikiLinks;
    }

    public void setWikiLinks(boolean wikiLinks) {
        this.wikiLinks = wikiLinks;
    }

    public boolean isTables() {
        return tables;
    }

    public void setTables(boolean tables) {
        this.tables = tables;
    }

    public boolean isSuppressHTMLBlocks() {
        return suppressHTMLBlocks;
    }

    public void setSuppressHTMLBlocks(boolean suppressHTMLBlocks) {
        this.suppressHTMLBlocks = suppressHTMLBlocks;
    }

    public boolean isSuppressInlineHTML() {
        return suppressInlineHTML;
    }

    public void setSuppressInlineHTML(boolean suppressInlineHTML) {
        this.suppressInlineHTML = suppressInlineHTML;
    }
}
