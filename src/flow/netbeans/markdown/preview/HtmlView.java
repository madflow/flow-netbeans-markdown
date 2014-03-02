
package flow.netbeans.markdown.preview;

import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import org.netbeans.api.annotations.common.CheckForNull;

/**
 *
 * @author Holger Stenger
 */
public interface HtmlView {
    static final String PROP_STATUS_MESSAGE = "StatusMessage";

    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);

    JComponent getComponent();

    void setContent(String content);

    boolean isHtmlFullySupported();

    @CheckForNull
    String getStatusMessage();
}
