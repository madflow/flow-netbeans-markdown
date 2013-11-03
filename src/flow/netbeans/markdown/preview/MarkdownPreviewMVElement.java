package flow.netbeans.markdown.preview;

import flow.netbeans.markdown.csl.MarkdownLanguageConfig;
import flow.netbeans.markdown.options.MarkdownGlobalOptions;
import java.awt.EventQueue;
import java.io.IOException;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLDocument;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.openide.awt.UndoRedo;
import org.openide.cookies.EditorCookie;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.text.NbDocument;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.openide.windows.TopComponent;
import org.pegdown.PegDownProcessor;

/**
 *
 * @author Holger
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

    private final JPanel toolbar;

    private final RequestProcessor.Task updateTask;

    private final DocumentListener sourceDocListener;

    private final FileObject sourceFile;

    private StyledDocument sourceDoc;

    private MultiViewElementCallback callback;

    public MarkdownPreviewMVElement(Lookup context) {
        this.context = context;
        this.callback = null;
        this.sourceDoc = null;
        this.sourceDocListener = new DocumentListener() {
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
        };
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
        previewTextPane.setText("<html><head></head><body>Initializing...</body></html>");

        previewScrollPane = new JScrollPane(previewTextPane);
        previewScrollPane.setBorder(BorderFactory.createEmptyBorder());

        toolbar = new JPanel();
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
            final String sourceText = getSourceText(localSourceDoc);
            if (sourceText != null) {
                final String previewText = convertToHtml(sourceText);
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if (sourceFile != null) {
                            previewTextPane.setText(sourceText);
                            HTMLDocument previewDoc = (HTMLDocument) previewTextPane.getDocument();
                            previewDoc.setBase(sourceFile.toURL());
                        }
                        previewTextPane.setText(previewText);
                    }
                });
            }
        }
    }

    private String convertToHtml(final String sourceText) {
        MarkdownGlobalOptions markdownOptions = MarkdownGlobalOptions.getInstance();
        PegDownProcessor markdownProcessor = new PegDownProcessor(markdownOptions.getExtensionsValue());
        String bodyText = markdownProcessor.markdownToHtml(sourceText);
        final String previewText = "<html>\n<head>\n</head>\n<body>\n" + bodyText + "</body>\n</html>";
        return previewText;
    }

    private String getSourceText(final StyledDocument localSourceDoc) {
        final String[] sourceTexts = new String[] {null};
        NbDocument.runAtomic(localSourceDoc, new Runnable() {
            @Override
            public void run() {
                try {
                    final String sourceText = localSourceDoc.getText(0, localSourceDoc.getLength());
                    sourceTexts[0] = sourceText;
                }
                catch (BadLocationException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        });
        return sourceTexts[0];
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
}
