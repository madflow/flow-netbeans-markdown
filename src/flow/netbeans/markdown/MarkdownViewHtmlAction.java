package flow.netbeans.markdown;

import flow.netbeans.markdown.api.RenderOption;
import flow.netbeans.markdown.api.Renderable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.EnumSet;
import java.util.Set;
import org.netbeans.api.queries.FileEncodingQuery;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.awt.HtmlBrowser.URLDisplayer;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

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

    public MarkdownViewHtmlAction(MarkdownDataObject context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        try {
            Renderable renderable = context.getLookup().lookup(Renderable.class);

            Set<RenderOption> renderOptions = EnumSet.of(
                    RenderOption.PREFER_EDITOR,
                    RenderOption.RESOLVE_IMAGE_URLS);
            String htmlText = renderable.renderAsHtml(renderOptions);

            File temp = File.createTempFile("preview-" + context.getPrimaryFile().getName(), ".html");
            temp.deleteOnExit();

            // get file encoding
            Charset encoding = FileEncodingQuery.getEncoding(context.getPrimaryFile());
            PrintStream out = new PrintStream(new FileOutputStream(temp), false, encoding.name());
            out.print(htmlText);
            out.close();

            URL url = temp.toURI().toURL();
            URLDisplayer.getDefault().showURLExternal(url);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
