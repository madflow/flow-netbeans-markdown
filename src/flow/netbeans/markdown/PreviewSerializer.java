package flow.netbeans.markdown;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import org.pegdown.LinkRenderer;
import org.pegdown.ToHtmlSerializer;
import org.pegdown.ast.AutoLinkNode;
import org.pegdown.ast.ExpLinkNode;
import org.pegdown.ast.MailLinkNode;
import org.pegdown.ast.RefLinkNode;
import org.pegdown.ast.SuperNode;
import org.pegdown.ast.WikiLinkNode;

/**
 *
 * @author Holger Stenger
 */
public class PreviewSerializer extends ToHtmlSerializer {
    private final URL baseUrl;

    private final boolean resolveImageUrls;

    public PreviewSerializer(final URL baseUrl) {
        this(baseUrl, true, false);
    }

    public PreviewSerializer(URL baseUrl, boolean resolveImageUrls, boolean resolveLinkUrls) {
        super(new PreviewLinkRenderer(baseUrl, resolveLinkUrls));
        this.baseUrl = baseUrl;
        this.resolveImageUrls = resolveImageUrls;
    }

    private String resolveUrl(final String url) {
        try {
            return baseUrl.toURI().resolve(url).toURL().toExternalForm();
        }
        catch (URISyntaxException ex) {
        }
        catch (MalformedURLException ex) {
        }
        return url;
    }

    private String resolveImageUrl(final String url) {
        if (resolveImageUrls) {
            return resolveUrl(url);
        }
        else {
            return url;
        }
    }

    @Override
    protected void printImageTag(SuperNode imageNode, String url) {
        printer.print("<img src=\"").print(resolveImageUrl(url)).print("\"  alt=\"")
                .printEncoded(printChildrenToString(imageNode)).print("\"/>");
    }
}
