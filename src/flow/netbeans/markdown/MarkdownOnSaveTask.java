package flow.netbeans.markdown;

import flow.netbeans.markdown.csl.MarkdownLanguageConfig;
import flow.netbeans.markdown.options.MarkdownPanel;
import java.io.IOException;
import javax.swing.text.Document;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.netbeans.spi.editor.document.OnSaveTask;
import org.openide.util.Exceptions;
import org.openide.util.NbPreferences;

/**
 *
 * @author junichi11
 */
public final class MarkdownOnSaveTask implements OnSaveTask{
    
    private MarkdownDataObject dataObject;

    private MarkdownOnSaveTask(Context context) {
        Document document = context.getDocument();
        if (document != null) {
            dataObject = (MarkdownDataObject) NbEditorUtilities.getDataObject(document);
        }
    }

    @Override
    public void performTask() {
        if (dataObject == null) {
            return;
        }

        // view html
        if (NbPreferences.forModule(MarkdownPanel.class).getBoolean("VIEW_HTML_ON_SAVE", false)) {
            try {
                MarkdownViewHtmlAction viewAction = new MarkdownViewHtmlAction(dataObject);
                viewAction.actionPerformed(null);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    @Override
    public void runLocked(Runnable run) {
        run.run();
    }

    @Override
    public boolean cancel() {
        return true;
    }
    
    @MimeRegistration(mimeType = MarkdownLanguageConfig.MIME_TYPE, service = OnSaveTask.Factory.class, position = 1500)
    public static final class FactoryImpl implements Factory {

        @Override
        public OnSaveTask createTask(Context context) {
            return new MarkdownOnSaveTask(context);
        }
    }
    
}
