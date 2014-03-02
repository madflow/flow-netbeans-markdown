
package flow.netbeans.markdown.preview;

import flow.netbeans.markdown.options.MarkdownGlobalOptions;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Holger Stenger
 */
public class HtmlViewFactory {
    private static final Logger LOG = Logger.getLogger(HtmlViewFactory.class.getName());

    public HtmlViewFactory() {
    }

    public HtmlView createHtmlView() {
        if (MarkdownGlobalOptions.getInstance().isFXHtmlViewEnabled()) {
            try {
                return createFXHtmlView();
            } catch (UnsupportedOperationException ex) {
                LOG.log(Level.INFO, "Falling back to Swing implementation", ex);
            }
        }
        return createDefaultHtmlView();
    }

    public HtmlView createDefaultHtmlView() {
        return new SwingHtmlView();
    }

    private HtmlView createFXHtmlView() throws UnsupportedOperationException {
        ClassLoader cl = getClassLoader();
        checkFXFeatures(cl);
        return createFXHtmlView(cl);
    }

    private ClassLoader getClassLoader() throws UnsupportedOperationException {
        return HtmlViewFactory.class.getClassLoader();
    }

    private HtmlView createFXHtmlView(ClassLoader cl) throws UnsupportedOperationException {
        try {
            Class<?> htmlViewClass = cl.loadClass("flow.netbeans.markdown.preview.ext.FXHtmlView");
            Constructor<?> htmlViewConstructor = htmlViewClass.getConstructor();
            return (HtmlView) htmlViewConstructor.newInstance();
        }
        catch (Throwable ex) {
            throw new UnsupportedOperationException(ex);
        }
    }

    public void checkFXFeatures(ClassLoader cl) throws UnsupportedOperationException {
        try {
            Class<?> platformClass = cl.loadClass("javafx.application.Platform");
            Method setImplicitExitMethod = platformClass.getMethod("setImplicitExit", boolean.class);
            Class<?> jfxPanelClass = cl.loadClass("javafx.embed.swing.JFXPanel");
        }
        catch (ClassNotFoundException ex) {
            throw new UnsupportedOperationException(ex);
        }
        catch (NoSuchMethodException ex) {
            throw new UnsupportedOperationException(ex);
        }
        catch (SecurityException ex) {
            throw new UnsupportedOperationException(ex);
        }
    }
}
