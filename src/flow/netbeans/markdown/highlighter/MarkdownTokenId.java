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

    ABBREVIATION,
    AUTOLINK,
    BLOCKQUOTE,
    BULLETLIST,
    CODE,
    EMPH,
    EXPIMAGE,
    EXPLINK,
    HEADER1,
    HEADER2,
    HEADER3,
    HEADER4,
    HEADER5,
    HEADER6,
    HORIZONTALRULE,
    HTMLBLOCK,
    INLINEHTML,
    LISTITEM,
    MAILLINK,
    ORDEREDLIST,
    PLAIN,
    STRONG,
    TABLE,
    VERBATIM,
    WIKILINK,
    DEFINITION,
    DEFINITION_LIST,
    DEFINITION_TERM,
    REFERENCE,
    QUOTED,
    REF_IMAGE,
    REF_LINK,
    WHITESPACE;
        
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

    @Override
    public String primaryCategory() {
        return this.name().toLowerCase();
    }

    public static Language<MarkdownTokenId> language() {
        return LANGUAGE;
    }
}