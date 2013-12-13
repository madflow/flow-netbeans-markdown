/*
 * Generate HTML from a Mardown source file.
 */
package flow.netbeans.markdown;

import flow.netbeans.markdown.api.RenderOption;
import flow.netbeans.markdown.api.Renderable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

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
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        try {
            Renderable renderable = context.getLookup().lookup(Renderable.class);

            Set<RenderOption> renderOptions = Collections.emptySet();
            String htmlText = renderable.renderAsHtml(renderOptions);

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
                
                PrintStream out = new PrintStream(new FileOutputStream(fileName));
                out.print(htmlText);
                out.close();
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
