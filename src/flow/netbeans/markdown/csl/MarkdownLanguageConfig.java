package flow.netbeans.markdown.csl;

import flow.netbeans.markdown.highlighter.MarkdownTokenId;
import org.netbeans.api.lexer.Language;
import org.netbeans.modules.csl.api.StructureScanner;
import org.netbeans.modules.csl.spi.DefaultLanguageConfig;
import org.netbeans.modules.csl.spi.LanguageRegistration;
import org.netbeans.modules.parsing.spi.Parser;

@LanguageRegistration(mimeType = MarkdownLanguageConfig.MIME_TYPE)
public class MarkdownLanguageConfig extends DefaultLanguageConfig {

    public static final String MIME_TYPE = "text/x-markdown"; //NOI18N

    @Override
    public Language<MarkdownTokenId> getLexerLanguage() {
        return MarkdownTokenId.language();
    }

    @Override
    public String getDisplayName() {
        return "Markdown"; //NOI18N
    }

    @Override
    public Parser getParser() {
        return new MarkdownParser();
    }

    @Override
    public boolean hasStructureScanner() {
        return true;
    }

    @Override
    public StructureScanner getStructureScanner() {
        return new MarkdownStructureScanner();
    }
}
