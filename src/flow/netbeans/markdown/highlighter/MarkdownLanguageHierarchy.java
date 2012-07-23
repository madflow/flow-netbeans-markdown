package flow.netbeans.markdown.highlighter;

import java.util.Collection;
import java.util.EnumSet;
import org.netbeans.spi.lexer.LanguageHierarchy;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

public class MarkdownLanguageHierarchy extends LanguageHierarchy<MarkdownTokenId> {

    @Override
    protected synchronized Collection<MarkdownTokenId> createTokenIds () {
        return EnumSet.allOf (MarkdownTokenId.class);
    }

    @Override
    protected Lexer<MarkdownTokenId> createLexer (LexerRestartInfo<MarkdownTokenId> info) {
        return new MarkdownLexer(info);
    }

    @Override
    protected String mimeType () {
        return "text/x-markdown";
    }

}
