package flow.netbeans.markdown;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import org.pegdown.LinkRenderer;
import org.pegdown.ToHtmlSerializer;
import org.pegdown.ast.SuperNode;

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

    protected void printImageTag(SuperNode imageNode, String url) {
        printer.print("<img src=\"").print(resolveImageUrl(url)).print("\"  alt=\"")
                .printEncoded(printChildrenToString(imageNode)).print("\" />");
    }
    
    @Override
    protected void printImageTag(LinkRenderer.Rendering rendering) {
        printer.print("<img");
        printAttribute("src", resolveImageUrl(rendering.href));
        printAttribute("alt", rendering.text);
        for (LinkRenderer.Attribute attr : rendering.attributes) {
            printAttribute(attr.name, attr.value);
        }
        printer.print(" />");
    }
    
    private void printAttribute(String name, String value) {
        printer.print(' ').print(name).print('=').print('"').print(value).print('"');
    }
}
