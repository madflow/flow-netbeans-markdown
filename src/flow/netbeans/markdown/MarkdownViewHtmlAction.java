package flow.netbeans.markdown;

import flow.netbeans.markdown.options.MarkdownGlobalOptions;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.nio.charset.Charset;
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
import org.openide.awt.HtmlBrowser.URLDisplayer;
import org.openide.cookies.EditorCookie;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.pegdown.PegDownProcessor;

@ActionID(category = "File",
        id = "flow.netbeans.markdown.MarkdownViewHtmlAction")
@ActionRegistration(displayName = "#CTL_MarkdownViewHtmlAction", iconBase = "flow/netbeans/markdown/resources/action-view.png")
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
            File temp = File.createTempFile(context.getPrimaryFile().getName(), ".html");

            PrintStream out;
            if (document != null) {
                // get file encoding
                Charset encoding = FileEncodingQuery.getEncoding(f);
                out = new PrintStream(new FileOutputStream(temp), false, encoding.name());
            } else {
                out = new PrintStream(new FileOutputStream(temp));
            }
            out.print(full);
            out.close();

            URLDisplayer.getDefault().showURL(new URL("file://" + temp.toString()));

            temp.deleteOnExit();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (BadLocationException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
