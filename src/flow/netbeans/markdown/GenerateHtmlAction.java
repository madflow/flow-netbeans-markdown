/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flow.netbeans.markdown;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.util.NbBundle.Messages;
import com.cforcoding.jmd.MarkDown;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

@ActionID(category = "File",
id = "flow.netbeans.markdown.GenerateHtmlAction")
@ActionRegistration(displayName = "#CTL_GenerateHtmlAction")
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 0),
    @ActionReference(path = "Loaders/text/x-markdown/Actions", position = 1150)
})
@Messages("CTL_GenerateHtmlAction=Generate HTML File")
public final class GenerateHtmlAction implements ActionListener {

    private final MarkdownDataObject context;

    public GenerateHtmlAction(MarkdownDataObject context) throws IOException {
        this.context = context;
        MarkDown markdownParser = new MarkDown();

        FileObject f = context.getPrimaryFile();
        String markdownSource = f.asText();
        String html = markdownParser.transform(markdownSource);

        JFileChooser fileChooser = new JFileChooser("user.home");
        int option = fileChooser.showSaveDialog(fileChooser);
        int result = 0;
        String fileName = "";

        if (option == JFileChooser.APPROVE_OPTION) {
            fileName = fileChooser.getSelectedFile().toString();

            if (fileChooser.getSelectedFile().exists()) {
                result = JOptionPane.showConfirmDialog(fileChooser, "The file exists, overwrite?", "Existing file", JOptionPane.YES_NO_CANCEL_OPTION);
            }

            switch (result) {
                case JOptionPane.NO_OPTION:
                    return;
                case JOptionPane.CANCEL_OPTION:
                    return;
            }

            try {
                PrintStream out = new PrintStream(new FileOutputStream(fileName));
                out.print(html);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void actionPerformed(ActionEvent ev) {
    }
}
