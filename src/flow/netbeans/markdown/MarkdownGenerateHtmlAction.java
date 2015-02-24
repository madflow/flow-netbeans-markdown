/*
 * Generate HTML from a Mardown source file.
 */
package flow.netbeans.markdown;

import flow.netbeans.markdown.api.RenderOption;
import flow.netbeans.markdown.api.Renderable;
import flow.netbeans.markdown.options.MarkdownGlobalOptions;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
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

    public MarkdownGenerateHtmlAction(MarkdownDataObject context) {
        this.context = context;
    }

    public void exportOnSave() {
        try {
            Renderable renderable = context.getLookup().lookup(Renderable.class);
            Set<RenderOption> renderOptions = EnumSet.of(RenderOption.PREFER_EDITOR);
            String htmlText = renderable.renderAsHtml(renderOptions);

            FileObject markdownFile = context.getPrimaryFile();
            String htmlFile = markdownFile.getParent().getPath() + File.separator + markdownFile.getName() + ".html";

            PrintStream out = new PrintStream(new FileOutputStream(htmlFile));
            out.print(htmlText);
            out.close();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        try {
            Renderable renderable = context.getLookup().lookup(Renderable.class);

            Set<RenderOption> renderOptions = Collections.emptySet();
            FileObject markdownFile = context.getPrimaryFile();

            String fileName = markdownFile.getName();
            String htmlText = renderable.renderAsHtml(renderOptions);
            String saveTo = MarkdownGlobalOptions.getInstance().isSaveInSourceDir() ? markdownFile.getParent().getPath() : "user.home";

            JFileChooser fileChooser = new JFileChooser(saveTo);
            fileChooser.setSelectedFile(new File(fileName + ".html"));

            int option = fileChooser.showSaveDialog(fileChooser);
            int result = 0;

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
