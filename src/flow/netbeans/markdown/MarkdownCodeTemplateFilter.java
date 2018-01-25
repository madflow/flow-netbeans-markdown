package flow.netbeans.markdown;

import flow.netbeans.markdown.csl.MarkdownLanguageConfig;
import java.util.Collections;
import java.util.List;
import javax.swing.text.JTextComponent;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.lib.editor.codetemplates.api.CodeTemplate;
import org.netbeans.lib.editor.codetemplates.spi.CodeTemplateFilter;

/**
 *
 * @author junichi11
 */
public class MarkdownCodeTemplateFilter implements CodeTemplateFilter {

    private static final String MD_CODE = "md-code"; // NOI18N

    @Override
    public boolean accept(CodeTemplate template) {
        return true;
    }

    @MimeRegistration(mimeType = MarkdownLanguageConfig.MIME_TYPE, service = CodeTemplateFilter.ContextBasedFactory.class)
    public static final class Factory implements CodeTemplateFilter.ContextBasedFactory {

        @Override
        public List<String> getSupportedContexts() {
            return Collections.singletonList(MD_CODE);
        }

        @Override
        public CodeTemplateFilter createFilter(JTextComponent component, int offset) {
            return new MarkdownCodeTemplateFilter();
        }

    }

}
