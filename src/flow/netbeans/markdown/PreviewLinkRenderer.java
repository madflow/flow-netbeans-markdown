
package flow.netbeans.markdown;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import org.pegdown.LinkRenderer;
import org.pegdown.ast.AutoLinkNode;
import org.pegdown.ast.ExpLinkNode;
import org.pegdown.ast.MailLinkNode;
import org.pegdown.ast.RefLinkNode;
import org.pegdown.ast.WikiLinkNode;

/**
 *
 * @author Holger Stenger
 */
public class PreviewLinkRenderer extends LinkRenderer {
    private final URL baseUrl;

    private final boolean resolveLinkUrls;

    public PreviewLinkRenderer(URL baseUrl, boolean resolveLinkUrls) {
        super();
        this.baseUrl = baseUrl;
        this.resolveLinkUrls = resolveLinkUrls;
    }

    @Override
    public LinkRenderer.Rendering render(AutoLinkNode node) {
        return transform(super.render(node));
    }

    @Override
    public LinkRenderer.Rendering render(MailLinkNode node) {
        return transform(super.render(node));
    }

    @Override
    public LinkRenderer.Rendering render(WikiLinkNode node) {
        return transform(super.render(node));
    }

    @Override
    public LinkRenderer.Rendering render(ExpLinkNode node, String text) {
        return transform(super.render(node, text));
    }

    @Override
    public LinkRenderer.Rendering render(RefLinkNode node, String url, String title, String text) {
        return transform(super.render(node, url, title, text));
    }

    private String resolveUrl(final String uriText) {
        try {
            return baseUrl.toURI().resolve(uriText).toURL().toExternalForm();
        }
        catch (URISyntaxException ex) {
        }
        catch (MalformedURLException ex) {
        }
        return uriText;
    }

    private LinkRenderer.Rendering transform(LinkRenderer.Rendering rendering) {
        if (resolveLinkUrls && (rendering.href != null)) {
            rendering.href = resolveUrl(rendering.href);
        }
        return rendering;
    }

}
