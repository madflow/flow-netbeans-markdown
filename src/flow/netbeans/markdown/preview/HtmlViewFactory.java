
package flow.netbeans.markdown.preview;

import flow.netbeans.markdown.options.MarkdownGlobalOptions;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.modules.InstalledFileLocator;

/**
 *
 * @author Holger Stenger
 */
public class HtmlViewFactory {
    private static final Logger LOG = Logger.getLogger(HtmlViewFactory.class.getName());

    private static final Memoizer<URLClassLoader> classLoader = new Memoizer<URLClassLoader>(new ClassLoaderCallable());

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
        try {
            return classLoader.get();
        }
        catch (ExecutionException ex) {
            throw new UnsupportedOperationException(ex);
        }
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

    private static class ClassLoaderCallable implements Callable<URLClassLoader> {
        private static final String CODE_NAME_BASE = "flow.netbeans.markdown";
        private static final String RELATIVE_PATH = "modules/ext/flow-netbeans-markdown-ext.jar";

        @Override
        public URLClassLoader call() throws UnsupportedOperationException {
            File extPath = getExtPath();
            List<File> fxPath = getFXRuntimePath();
            return createClassLoader(fxPath, extPath);
        }

        private File getExtPath() {
            final File extPath = InstalledFileLocator.getDefault().locate(
                    RELATIVE_PATH, CODE_NAME_BASE, false);
            if (extPath == null) {
                throw new UnsupportedOperationException("Extension JAR not found");
            }
            return extPath;
        }
        private List<File> getFXRuntimePath() {
            String javaHome = System.getProperty("java.home", null); // NOI18N
            if (javaHome == null) {
                return Collections.emptyList();
            }
            File fxPath = new File(new File(javaHome, "lib"), "jfxrt.jar").getAbsoluteFile(); // NOI18N
            if (fxPath.exists()) {
                return Collections.singletonList(fxPath);
            }
            fxPath = new File(new File(new File(javaHome, "lib"), "ext"), "jfxrt.jar").getAbsoluteFile(); // NOI18N
            if (fxPath.exists()) {
                return Collections.emptyList();
            }
            throw new UnsupportedOperationException("JavaFX Runtime not found");
        }

        private URLClassLoader createClassLoader(List<File> fxPath, File extPath) throws UnsupportedOperationException {
            try {
                List<URL> urls = new ArrayList<URL>();
                urls.add(extPath.toURI().toURL());
                for (File file : fxPath) {
                    urls.add(file.toURI().toURL());
                }
                return new URLClassLoader(urls.toArray(new URL[urls.size()]), HtmlViewFactory.class.getClassLoader());
            } catch (MalformedURLException ex) {
                throw new UnsupportedOperationException(ex);
            }
        }
    }
}
