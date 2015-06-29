/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flow.netbeans.markdown.options.editor;

import java.util.prefs.Preferences;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import org.netbeans.modules.options.editor.spi.PreferencesCustomizer;
import org.netbeans.modules.options.editor.spi.PreviewProvider;
import org.openide.text.CloneableEditorSupport;
import org.openide.util.HelpCtx;

/**
 *
 * @author Holger Stenger
 */
public class MarkdownFormattingCustomizer implements PreferencesCustomizer, PreviewProvider {

    private final String mimeType;
    private JEditorPane previewPane;

    public MarkdownFormattingCustomizer(String mimeType, Preferences p) {
        this.mimeType = mimeType;
    }

    @Override
    public String getId() {
        return PreferencesCustomizer.TABS_AND_INDENTS_ID;
    }

    @Override
    public String getDisplayName() {
        return "Markdown";
    }

    @Override
    public HelpCtx getHelpCtx() {
        return null;
    }

    @Override
    public JComponent getComponent() {
        return new JPanel();
    }

    @Override
    public JComponent getPreviewComponent() {
        if (previewPane == null) {
            previewPane = new JEditorPane();
            previewPane.setEditorKit(CloneableEditorSupport.getEditorKit(mimeType));
            previewPane.setText("# Header\n\nThis is the first paragraph.\n\n* list item 1\n* list item 2");
            previewPane.setEditable(false);
        }
        return previewPane;
    }

    @Override
    public void refreshPreview() {
    }

}
