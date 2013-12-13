
package flow.netbeans.markdown.api;

import java.io.IOException;
import java.util.Set;

/**
 *
 * @author Holger Stenger
 */
public interface Renderable {
    String renderAsHtml(Set<RenderOption> renderOptions) throws IOException;
}
