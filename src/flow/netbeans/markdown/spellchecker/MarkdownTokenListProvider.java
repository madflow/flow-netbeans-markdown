package flow.netbeans.markdown.spellchecker;

import flow.netbeans.markdown.csl.MarkdownLanguageConfig;
import javax.swing.text.Document;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.editor.BaseDocument;
import org.netbeans.modules.spellchecker.spi.language.TokenList;
import org.netbeans.modules.spellchecker.spi.language.TokenListProvider;

/**
 *
 * @author Holger Stenger
 */
@MimeRegistration(mimeType = MarkdownLanguageConfig.MIME_TYPE, 
                  service = TokenListProvider.class, position = 1000)
public class MarkdownTokenListProvider implements TokenListProvider {

    public MarkdownTokenListProvider() {
    }

    @Override
    public TokenList findTokenList(Document doc) {
        if (doc instanceof BaseDocument) {
            BaseDocument baseDoc = (BaseDocument) doc;
            final Object mimeType = baseDoc.getProperty("mimeType");
            if (MarkdownLanguageConfig.MIME_TYPE.equals(mimeType)) {
                return new MarkdownTokenList(doc);
            }
        }
        return null;
    }
}
