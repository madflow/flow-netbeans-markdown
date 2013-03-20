/*
 * Generate HTML from a Mardown source file.
 */
package flow.netbeans.markdown;

import flow.netbeans.markdown.options.MarkdownGlobalOptions;
import flow.netbeans.markdown.options.MarkdownPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.prefs.Preferences;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.util.NbBundle.Messages;
import org.openide.util.NbPreferences;
import org.pegdown.PegDownProcessor;

@ActionID(category = "File",
id = "flow.netbeans.markdown.GenerateHtmlAction")
@ActionRegistration(displayName = "#CTL_GenerateHtmlAction", iconBase = "flow/netbeans/markdown/resources/action-export.png")
@ActionReferences({
    @ActionReference(path = "Editors/text/x-markdown/Toolbars/Default", position = 270000),
    @ActionReference(path = "Editors/text/x-markdown/Popup", position = 0),
    @ActionReference(path = "Loaders/text/x-markdown/Actions", position = 250)
})
@Messages("CTL_GenerateHtmlAction=Generate HTML")
public final class MarkdownGenerateHtmlAction implements ActionListener {

    private final MarkdownDataObject context;

    public MarkdownGenerateHtmlAction(MarkdownDataObject context) throws IOException {
        this.context = context;

        Preferences prefs = NbPreferences.forModule(MarkdownPanel.class);
        MarkdownGlobalOptions markdownOptions = new MarkdownGlobalOptions(prefs);
        PegDownProcessor markdownProcessor = new PegDownProcessor(markdownOptions.getExtensionsValue());

        FileObject f = context.getPrimaryFile();
        String markdownSource = f.asText();
        String html = markdownProcessor.markdownToHtml(markdownSource);
        String full = prefs
                .get("HTML_TEMPLATE", MarkdownPanel.getDefaultHtmlTemplate())
                .replace("{%CONTENT%}", html.toString())
                .replace("{%TITLE%}", context.getPrimaryFile().getName());

        JFileChooser fileChooser = new JFileChooser("user.home");
        int option = fileChooser.showSaveDialog(fileChooser);
        int result = 0;
        String fileName;

        if (option == JFileChooser.APPROVE_OPTION) {
            fileName = fileChooser.getSelectedFile().toString();

            if (fileChooser.getSelectedFile().exists()) {
                result = JOptionPane.showConfirmDialog(fileChooser,
                        "The file exists, overwrite?", "Existing file",
                        JOptionPane.YES_NO_CANCEL_OPTION);
            }

            switch (result) {
                case JOptionPane.NO_OPTION:
                    return;
                case JOptionPane.CANCEL_OPTION:
                    return;
            }
            try {
                PrintStream out = new PrintStream(new FileOutputStream(fileName));
                out.print(full);
                out.close();
            } catch (IOException e) {
            }

        }
    }

    public void actionPerformed(ActionEvent ev) {
    }
}
