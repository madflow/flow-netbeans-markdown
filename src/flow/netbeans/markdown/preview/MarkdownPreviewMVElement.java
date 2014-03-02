package flow.netbeans.markdown.preview;

import flow.netbeans.markdown.MarkdownDataObject;
import flow.netbeans.markdown.MarkdownViewHtmlAction;
import flow.netbeans.markdown.api.RenderOption;
import flow.netbeans.markdown.api.Renderable;
import flow.netbeans.markdown.csl.MarkdownLanguageConfig;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLDocument;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.openide.awt.UndoRedo;
import org.openide.cookies.EditorCookie;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.openide.windows.TopComponent;

/**
 *
 * @author Holger Stenger
 */
@NbBundle.Messages("Preview=Preview")
@MultiViewElement.Registration(
        displayName = "#Preview",
        iconBase = "flow/netbeans/markdown/resources/text-x-generic.png",
        persistenceType = TopComponent.PERSISTENCE_ONLY_OPENED,
        mimeType = MarkdownLanguageConfig.MIME_TYPE,
        preferredID = "markdown.preview",
        position = 100)
public class MarkdownPreviewMVElement implements MultiViewElement {
    private static final RequestProcessor RP = new RequestProcessor(MarkdownPreviewMVElement.class);

    private final Object lock = new Object();

    private final Lookup context;

    private final JScrollPane previewScrollPane;

    private final JTextPane previewTextPane;

    private final JToolBar toolbar;

    private final RequestProcessor.Task updateTask;

    private final DocumentListener sourceDocListener;

    private final FileObject sourceFile;

    private StyledDocument sourceDoc;

    private MultiViewElementCallback callback;

    public MarkdownPreviewMVElement(Lookup context) {
        this.context = context;
        this.callback = null;
        this.sourceDoc = null;
        this.sourceDocListener = new DocumentHandler();
        this.updateTask = RP.create(new Runnable() {
            @Override
            public void run() {
                doUpdatePreview();
            }
        });

        DataObject dataObj = context.lookup(DataObject.class);
        if (dataObj != null) {
            sourceFile = dataObj.getPrimaryFile();
        }
        else {
            sourceFile = null;
        }

        previewTextPane = new JTextPane();
        previewTextPane.setEditable(false);
        previewTextPane.setContentType("text/html");
        previewTextPane.setText("<html><head></head><body></body></html>");

        previewScrollPane = new JScrollPane(previewTextPane);
        previewScrollPane.setBorder(BorderFactory.createEmptyBorder());

        toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.addSeparator();
        toolbar.add(new PreviewExternalAction(context));
    }

    @Override
    public JComponent getVisualRepresentation() {
        return previewScrollPane;
    }

    @Override
    public JComponent getToolbarRepresentation() {
        return toolbar;
    }

    @Override
    public Action[] getActions() {
        return new Action[0];
    }

    @Override
    public Lookup getLookup() {
        return context;
    }

    @Override
    public void componentOpened() {
    }

    @Override
    public void componentClosed() {
    }

    @Override
    public void componentShowing() {
        final EditorCookie ec = context.lookup(EditorCookie.class);
        if (ec != null) {
            RP.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        final StyledDocument localSourceDoc = ec.openDocument();
                        setSourceDocument(localSourceDoc);
                        doUpdatePreview();
                    }
                    catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            });
        }
    }

    @Override
    public void componentHidden() {
        setSourceDocument(null);
    }

    @Override
    public void componentActivated() {
    }

    private StyledDocument getSourceDocument() {
        synchronized (lock) {
            return sourceDoc;
        }
    }

    private void setSourceDocument(final StyledDocument newSourceDoc) {
        synchronized (lock) {
            if (this.sourceDoc != null) {
                this.sourceDoc.removeDocumentListener(sourceDocListener);
            }
            this.sourceDoc = newSourceDoc;
            if (this.sourceDoc != null) {
                this.sourceDoc.addDocumentListener(sourceDocListener);
            }
        }
    }

    private void updatePreview() {
        updateTask.schedule(1000);
    }

    private void doUpdatePreview() {
        final StyledDocument localSourceDoc = getSourceDocument();
        if (localSourceDoc != null) {
            final String previewText = renderPreview();
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if (sourceFile != null) {
                        HTMLDocument previewDoc = (HTMLDocument) previewTextPane.getDocument();
                        previewDoc.setBase(sourceFile.toURL());
                    }
                    previewTextPane.setText(previewText);
                    previewScrollPane.getVerticalScrollBar()
                            .setValue(previewScrollPane.getVerticalScrollBar().getMinimum());
                    previewScrollPane.getHorizontalScrollBar()
                            .setValue(previewScrollPane.getHorizontalScrollBar().getMinimum());
                }
            });
        }
    }

    private String renderPreview() {
        Renderable renderable = context.lookup(Renderable.class);
        String previewText;
        try {
            Set<RenderOption> renderOptions = EnumSet.of(
                    RenderOption.PREFER_EDITOR,
                    RenderOption.RESOLVE_IMAGE_URLS,
                    RenderOption.SWING_COMPATIBLE);
            previewText = renderable.renderAsHtml(renderOptions);
        } catch (IOException ex) {
            previewText = "Preview rendering failed: " + ex.getMessage();
        }
        return previewText;
    }

    @Override
    public void componentDeactivated() {
    }

    @Override
    public UndoRedo getUndoRedo() {
        return UndoRedo.NONE;
    }

    @Override
    public void setMultiViewCallback(MultiViewElementCallback callback) {
        this.callback = callback;
    }

    @Override
    public CloseOperationState canCloseElement() {
        return CloseOperationState.STATE_OK;
    }

    private class DocumentHandler implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            updatePreview();
}

        @Override
        public void removeUpdate(DocumentEvent e) {
            updatePreview();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updatePreview();
        }
    }

    @NbBundle.Messages({
        "NAME_PreviewExternalAction=Preview in external browser"
    })
    private static class PreviewExternalAction extends AbstractAction {
        private static final String ICON_PATH = "flow/netbeans/markdown/resources/action-view.png";

        private final Lookup context;

        public PreviewExternalAction(Lookup context) {
            super(Bundle.NAME_PreviewExternalAction(), ImageUtilities.loadImageIcon(ICON_PATH, false));
            putValue(Action.SHORT_DESCRIPTION, Bundle.NAME_PreviewExternalAction());
            this.context = context;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            MarkdownDataObject dataObject = context.lookup(MarkdownDataObject.class);
            MarkdownViewHtmlAction viewAction = new MarkdownViewHtmlAction(dataObject);
            viewAction.actionPerformed(null);
        }
    }
}
