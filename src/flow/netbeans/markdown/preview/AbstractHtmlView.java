
package flow.netbeans.markdown.preview;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.event.SwingPropertyChangeSupport;
import org.netbeans.api.annotations.common.CheckForNull;
import org.openide.awt.HtmlBrowser;

/**
 *
 * @author Holger Stenger
 */
public abstract class AbstractHtmlView implements HtmlView {
    private final PropertyChangeSupport pcs;

    private String statusMessage;

    public AbstractHtmlView() {
        pcs = new SwingPropertyChangeSupport(this, true);
        statusMessage = null;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    @Override
    @CheckForNull
    public String getStatusMessage() {
        return statusMessage;
    }

    protected void setStatusMessage(final String newValue) {
        final String oldValue = statusMessage;
        statusMessage = newValue;
        firePropertyChange(PROP_STATUS_MESSAGE, oldValue, newValue);
    }

    protected void firePropertyChange(final String propertyName, final Object oldValue, final Object newValue) {
        pcs.firePropertyChange(propertyName, oldValue, newValue);
    }

    protected void showURLExternal(final URL url) {
        HtmlBrowser.URLDisplayer.getDefault().showURLExternal(url);
    }

    protected void showURLExternal(final String url) {
        try {
            showURLExternal(new URL(url));
        }
        catch (MalformedURLException ex) {
            //Exceptions.printStackTrace(ex);
        }
    }
}
