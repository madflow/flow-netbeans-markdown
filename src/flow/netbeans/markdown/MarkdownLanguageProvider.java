package flow.netbeans.markdown;

import org.netbeans.api.lexer.InputAttributes;
import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.LanguagePath;
import org.netbeans.api.lexer.Token;
import org.netbeans.spi.lexer.LanguageEmbedding;
import org.netbeans.spi.lexer.LanguageProvider;
import flow.netbeans.markdown.highlighter.MarkdownLanguageHierarchy;
import flow.netbeans.markdown.highlighter.MarkdownTokenId;

@org.openide.util.lookup.ServiceProvider(service = org.netbeans.spi.lexer.LanguageProvider.class)
public class MarkdownLanguageProvider extends LanguageProvider {

    public Language<MarkdownTokenId> findLanguage(String mimeType) {
        if ("text/x-markdown".equals(mimeType)) {
            return new MarkdownLanguageHierarchy().language();
        }
        return null;
    }

    @Override
    public LanguageEmbedding<?> findLanguageEmbedding(
            Token arg0,
            LanguagePath arg1,
            InputAttributes arg2) {
        return null;
    }
}
