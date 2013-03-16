package flow.netbeans.markdown;

import flow.netbeans.markdown.options.MarkdownGlobalOptions;
import flow.netbeans.markdown.options.MarkdownOptionsPanelController;
import flow.netbeans.markdown.options.MarkdownPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.prefs.Preferences;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.awt.HtmlBrowser.URLDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.util.NbBundle.Messages;
import org.openide.util.NbPreferences;
import org.pegdown.PegDownProcessor;

@ActionID(category = "File",
id = "flow.netbeans.markdown.MarkdownViewHtmlAction")
@ActionRegistration(displayName = "#CTL_MarkdownViewHtmlAction")
@ActionReferences({
    @ActionReference(path = "Editors/text/x-markdown/Toolbars/Default", position = 270100),
    @ActionReference(path = "Editors/text/x-markdown/Popup", position = 0),
    @ActionReference(path = "Loaders/text/x-markdown/Actions", position = 251)
})
@Messages("CTL_MarkdownViewHtmlAction=View HTML")
public final class MarkdownViewHtmlAction implements ActionListener {

    private final MarkdownDataObject context;

    public MarkdownViewHtmlAction(MarkdownDataObject context) throws IOException {
        this.context = context;

        Preferences prefs = NbPreferences.forModule(MarkdownPanel.class);
        MarkdownGlobalOptions markdownOptions = new MarkdownGlobalOptions(prefs);
        PegDownProcessor markdownProcessor = new PegDownProcessor(markdownOptions.getExtensionsValue());

        FileObject f = context.getPrimaryFile();
        String markdownSource = f.asText();
        String html = markdownProcessor.markdownToHtml(markdownSource);

        String full = "<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"></head><body>" + html.toString() + "</body></html>";
        File temp = File.createTempFile(context.getPrimaryFile().getName(), ".html");

        PrintStream out = new PrintStream(new FileOutputStream(temp));
        out.print(full);
        out.close();

        URLDisplayer.getDefault().showURL(new URL("file://" + temp.toString()));

        temp.deleteOnExit();
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        // TODO use context
    }
}
