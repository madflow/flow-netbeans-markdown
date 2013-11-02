package flow.netbeans.markdown.highlighter;

import flow.netbeans.markdown.csl.MarkdownLanguageConfig;
import java.util.Collection;
import java.util.EnumSet;
import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.TokenId;
import org.netbeans.spi.lexer.LanguageHierarchy;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

public enum MarkdownTokenId implements TokenId {

    ABBREVIATION("abbreviation"),
    AUTOLINK("autolink"),
    BLOCKQUOTE("blockquote"),
    BULLETLIST("bulletlist"),
    CODE("code"),
    EMPH("emph"),
    EXPIMAGE("expimage"),
    EXPLINK("explink"),
    HEADER("header"),
    HORIZONTALRULE("horizontalrule"),
    HTMLBLOCK("htmlblock"),
    INLINEHTML("inlinehtml"),
    LISTITEM("listitem"),
    MAILLINK("maillink"),
    ORDEREDLIST("orderedlist"),
    PLAIN("plain"),
    STRONG("strong"),
    BOLD("bold"),
    ITALIC("italic"),
    TABLE("table"),
    TEXT("text"),
    VERBATIM("verbatim"),
    WIKILINK("wikilink"),
    DEFINITION("definition"),
    DEFINITION_LIST("definition_list"),
    DEFINITION_TERM("definition_term"),
    REFERENCE("reference"),
    QUOTED("quoted"),
    REF_IMAGE("ref_image"),
    REF_LINK("ref_link"),
    WHITESPACE("whitespace");
        
    private final String name;

    private static final Language<MarkdownTokenId> LANGUAGE = new LanguageHierarchy<MarkdownTokenId>() {

        @Override
        protected Collection<MarkdownTokenId> createTokenIds() {
            return EnumSet.allOf(MarkdownTokenId.class);
        }

        @Override
        protected Lexer<MarkdownTokenId> createLexer(LexerRestartInfo<MarkdownTokenId> info) {
            return MarkdownLexer.create(info);
        }

        @Override
        protected String mimeType() {
            return MarkdownLanguageConfig.MIME_TYPE;
        }
    }.language();

    MarkdownTokenId(String name) {
        this.name = name;
    }

    @Override
    public String primaryCategory() {
        return name;
    }

    public static Language<MarkdownTokenId> language() {
        return LANGUAGE;
    }
}