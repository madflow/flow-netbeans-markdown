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
 * Turns pegdown AST link nodes into rendered links, optionally resolving relative URLs to
 * absolute URLs using a given base URL.
 *
 * @author Holger Stenger
 */
public class PreviewLinkRenderer extends LinkRenderer {

    /** The base URL against which absolute URLs should be resolved. */
    private final URL baseUrl;

    /** Indicates whether relative URLs should be resolved against the base URL. */
    private final boolean resolveLinkUrls;

    /**
     * Create a new link renderer that will optionally resolve relative URLs to absolute URLs.
     *
     * @param baseUrl the base URL against which relative URLs will be resolved.
     * @param resolveLinkUrls if true, relative URLs in rendered links will be resolved to
     *          absolute URLs with the given base URL.
     */
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

    /**
     * Resolves relative URLs against a base URL to return an absolute URL.
     *
     * @param uriText the possibly relative URL to resolve
     * @return the absolute form of the provided relative URL; if the URL is already absolute, or if it
     *          would otherwise result in an invalid URL when resolved against the base URL, then the
     *          original string is returned unmodified
     */
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

    /**
     * Transforms the rendered link's href URL to its absolute form using a base URL,
     * if URL resolution is turned on.
     *
     * @param rendering the previously rendered link
     * @return if URL resolution is turned on and the link is relative, then the rendered
     *          link with the href URL in its absolute form using the base URL provided to
     *          this renderer; otherwise the original rendered link
     */
    private LinkRenderer.Rendering transform(LinkRenderer.Rendering rendering) {
        if (resolveLinkUrls && (rendering.href != null)) {
            LinkRenderer.Rendering resolvedRendering = new LinkRenderer.Rendering(
                resolveUrl(rendering.href), rendering.text);
            for (LinkRenderer.Attribute attr : rendering.attributes) {
              resolvedRendering.withAttribute(attr);
            }
            return resolvedRendering;
        } else {
          return rendering;
        }
    }

}
