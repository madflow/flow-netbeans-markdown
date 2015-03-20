package flow.netbeans.markdown;

import flow.netbeans.markdown.csl.MarkdownLanguageConfig;
import java.io.IOException;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.text.MultiViewEditorElement;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataNode;
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