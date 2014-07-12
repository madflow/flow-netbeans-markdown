package flow.netbeans.markdown;

import org.pegdown.LinkRenderer;
import org.pegdown.ToHtmlSerializer;

/**
 *
 * @author madflow
 */
public class ExportSerializer extends ToHtmlSerializer {

    public ExportSerializer(LinkRenderer linkRenderer) {
        super(linkRenderer);
    }
    
    @Override
    protected void printImageTag(LinkRenderer.Rendering rendering) {
        printer.print("<img");
        printAttribute("src", rendering.href);
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
