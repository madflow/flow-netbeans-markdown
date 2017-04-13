package flow.netbeans.markdown;

import flow.netbeans.markdown.csl.MarkdownLanguageConfig;
import java.io.IOException;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.text.MultiViewEditorElement;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.MIMEResolver;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ProxyLookup;
import org.openide.windows.TopComponent;

@NbBundle.Messages("Markdown=Markdown")
@MIMEResolver.ExtensionRegistration(
        displayName = "flow.netbeans.markdown.resources.Bundle#Services/MIMEResolver/MarkdownResolver.xml",
        mimeType = MarkdownLanguageConfig.MIME_TYPE,
        extension = {"md", "MD", "markdown", "MARKDOWN", "mkd", "MKD"},
        position = 7314
)
@DataObject.Registration(
        displayName = "#Markdown",
        iconBase = "flow/netbeans/markdown/resources/markdown-mark-16x16.png",
        mimeType = MarkdownLanguageConfig.MIME_TYPE
)
@ActionReferences({
    @ActionReference(
            id = @ActionID(category = "System", id = "org.openide.actions.OpenAction"),
            path = "Loaders/" + MarkdownLanguageConfig.MIME_TYPE + "/Actions",
            position = 100,
            separatorAfter = 200
    ),
    @ActionReference(
            id = @ActionID(category = "Edit", id = "org.openide.actions.CutAction"),
            path = "Loaders/" + MarkdownLanguageConfig.MIME_TYPE + "/Actions",
            position = 300
    ),
    @ActionReference(
            id = @ActionID(category = "Edit", id = "org.openide.actions.CopyAction"),
            path = "Loaders/" + MarkdownLanguageConfig.MIME_TYPE + "/Actions",
            position = 400,
            separatorAfter = 500
    ),
    @ActionReference(
            id = @ActionID(category = "Edit", id = "org.openide.actions.DeleteAction"),
            path = "Loaders/" + MarkdownLanguageConfig.MIME_TYPE + "/Actions",
            position = 600
    ),
    @ActionReference(
            id = @ActionID(category = "System", id = "org.openide.actions.RenameAction"),
            path = "Loaders/" + MarkdownLanguageConfig.MIME_TYPE + "/Actions",
            position = 700,
            separatorAfter = 800
    ),
    @ActionReference(
            id = @ActionID(category = "System", id = "org.openide.actions.SaveAsTemplateAction"),
            path = "Loaders/" + MarkdownLanguageConfig.MIME_TYPE + "/Actions",
            position = 900,
            separatorAfter = 1000
    ),
    @ActionReference(
            id = @ActionID(category = "System", id = "org.openide.actions.FileSystemAction"),
            path = "Loaders/" + MarkdownLanguageConfig.MIME_TYPE + "/Actions",
            position = 1100,
            separatorAfter = 1200
    ),
    @ActionReference(
            id = @ActionID(category = "System", id = "org.openide.actions.ToolsAction"),
            path = "Loaders/" + MarkdownLanguageConfig.MIME_TYPE + "/Actions",
            position = 1300
    ),
    @ActionReference(
            id = @ActionID(category = "System", id = "org.openide.actions.PropertiesAction"),
            path = "Loaders/" + MarkdownLanguageConfig.MIME_TYPE + "/Actions",
            position = 1400
    )
})
public class MarkdownDataObject extends MultiDataObject {
    private static final long serialVersionUID = 1L;

    private final Lookup lookup;

    private final InstanceContent lookupContent;

    public MarkdownDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        registerEditor(MarkdownLanguageConfig.MIME_TYPE, true);

        lookupContent = new InstanceContent();
        lookupContent.add(new RenderableImpl(this));

        lookup = new ProxyLookup(getCookieSet().getLookup(), new AbstractLookup(lookupContent));
    }

    @Override
    protected Node createNodeDelegate() {
        return new DataNode(this, Children.LEAF, getLookup());
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }

    @Override
    protected int associateLookup() {
        return 1;
    }

    @NbBundle.Messages("Source=&Source")
    @MultiViewElement.Registration(
            displayName="#Source",
            iconBase="flow/netbeans/markdown/resources/markdown-mark-16x16.png",
            persistenceType=TopComponent.PERSISTENCE_ONLY_OPENED,
            mimeType=MarkdownLanguageConfig.MIME_TYPE,
            preferredID="markdown.source",
            position=1
    )
    public static MultiViewEditorElement createMultiViewEditorElement(Lookup context) {
        return new MultiViewEditorElement(context);
    }
}