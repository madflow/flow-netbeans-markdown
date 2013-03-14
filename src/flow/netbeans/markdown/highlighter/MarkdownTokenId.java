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

    HEADER("header"),
    LIST("list"),
    BLOCKQUOTE("blockquote"),
    REMOVED("removed"),
    PLAIN("plain"),
    BOLD("bold");

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

    public static Language language() {
        return LANGUAGE;
    }
    private String name;

    MarkdownTokenId(
            String name) {
        this.name = name;
    }

    @Override
    public String primaryCategory() {
        return name;
    }
}
