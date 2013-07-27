/*
 * Generate HTML from a Mardown source file.
 */
package flow.netbeans.markdown;

import flow.netbeans.markdown.options.MarkdownGlobalOptions;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import org.netbeans.api.editor.EditorRegistry;
import org.netbeans.api.queries.FileEncodingQuery;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.cookies.EditorCookie;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
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
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        try {
            MarkdownGlobalOptions markdownOptions = MarkdownGlobalOptions.getInstance();
            PegDownProcessor markdownProcessor = new PegDownProcessor(markdownOptions.getExtensionsValue());

            // get document
            EditorCookie ec = context.getLookup().lookup(EditorCookie.class);
            StyledDocument document = ec.getDocument();

            // get file object
            FileObject f = context.getPrimaryFile();
            if (f == null) {
                return;
            }

            // get selected text
            JTextComponent editor = EditorRegistry.lastFocusedComponent();
            String selectedText = "";
            if (editor != null) {
                Document lastFocusedDocument = editor.getDocument();
                if (document == lastFocusedDocument) {
                    selectedText = editor.getSelectedText();
                }
            }

            String markdownSource = "";
            if (document != null) {
                if (selectedText != null && !selectedText.isEmpty()) {
                    markdownSource = selectedText;
                } else {
                    markdownSource = document.getText(0, document.getLength());
                }
            } else {
                markdownSource = f.asText();
            }
            String html = markdownProcessor.markdownToHtml(markdownSource);
            String full = markdownOptions.getHtmlTemplate()
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

                PrintStream out;
                if (document != null) {
                    // get file encoding
                    Charset encoding = FileEncodingQuery.getEncoding(f);
                    out = new PrintStream(new FileOutputStream(fileName), false, encoding.name());
                } else {
                    out = new PrintStream(new FileOutputStream(fileName));
                }
                out.print(full);
                out.close();
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (BadLocationException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
