package flow.netbeans.markdown.csl;

import flow.netbeans.markdown.highlighter.MarkdownTokenId;
import org.netbeans.api.lexer.Language;
import org.netbeans.modules.csl.spi.DefaultLanguageConfig;
import org.netbeans.modules.csl.spi.LanguageRegistration;

@LanguageRegistration(mimeType = MarkdownLanguageConfig.MIME_TYPE)
public class MarkdownLanguageConfig extends DefaultLanguageConfig {

    public static final String MIME_TYPE = "text/x-markdown"; //NOI18N

    @Override
    public Language getLexerLanguage() {
        return MarkdownTokenId.language();
    }

    @Override
    public String getDisplayName() {
        return "MARKDOWN"; //NOI18N
    }
}
