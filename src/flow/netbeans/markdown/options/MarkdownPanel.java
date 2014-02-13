package flow.netbeans.markdown.options;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JEditorPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import org.netbeans.api.editor.DialogBinding;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileSystem;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.text.CloneableEditorSupport;
import org.openide.util.Exceptions;

public final class MarkdownPanel extends javax.swing.JPanel {

    private final MarkdownOptionsPanelController controller;
    private static final long serialVersionUID = -2655146103696320933L;

    MarkdownPanel(MarkdownOptionsPanelController controller) {
        this.controller = controller;
        initComponents();
        setMimeType(HTML_TEMPLATE, "text/html", "html");

        ActionListener actionListener = new ActionHandler();
        ABBREVIATIONS.addActionListener(actionListener);
        AUTOLINKS.addActionListener(actionListener);
        DEFINITION_LISTS.addActionListener(actionListener);
        FENCED_CODE_BLOCKS.addActionListener(actionListener);
        HARDWRAPS.addActionListener(actionListener);
        HTML_BLOCK_SUPPRESSION.addActionListener(actionListener);
        INLINE_HTML_SUPPRESSION.addActionListener(actionListener);
        QUOTES.addActionListener(actionListener);
        SMARTS.addActionListener(actionListener);
        TABLES.addActionListener(actionListener);
        WIKILINKS.addActionListener(actionListener);
        SAVE_IN_SOURCE_DIR.addActionListener(actionListener);
        FX_HTML_VIEW_ENABLED.addActionListener(actionListener);

        DocumentListener documentListener = new DocumentHandler();
        HTML_TEMPLATE.getDocument().addDocumentListener(documentListener);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TABS = new javax.swing.JTabbedPane();
        EXTENSIONS_PANEL = new javax.swing.JPanel();
        EXTENSIONS_PANEL_HEADER = new javax.swing.JLabel();
        SMARTS = new javax.swing.JCheckBox();
        QUOTES = new javax.swing.JCheckBox();
        ABBREVIATIONS = new javax.swing.JCheckBox();
        HARDWRAPS = new javax.swing.JCheckBox();
        AUTOLINKS = new javax.swing.JCheckBox();
        TABLES = new javax.swing.JCheckBox();
        DEFINITION_LISTS = new javax.swing.JCheckBox();
        FENCED_CODE_BLOCKS = new javax.swing.JCheckBox();
        HTML_BLOCK_SUPPRESSION = new javax.swing.JCheckBox();
        INLINE_HTML_SUPPRESSION = new javax.swing.JCheckBox();
        WIKILINKS = new javax.swing.JCheckBox();
        HTML_EXPORT_PANEL = new javax.swing.JPanel();
        HTML_PANEL_HEADER = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        HTML_TEMPLATE = new javax.swing.JEditorPane();
        MISC_PANEL = new javax.swing.JPanel();
        VIEW_HTML_ON_SAVE = new javax.swing.JCheckBox();
        SAVE_IN_SOURCE_DIR = new javax.swing.JCheckBox();
        FX_HTML_VIEW_ENABLED = new javax.swing.JCheckBox();

        org.openide.awt.Mnemonics.setLocalizedText(EXTENSIONS_PANEL_HEADER, org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.EXTENSIONS_PANEL_HEADER.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(SMARTS, org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.SMARTS.text")); // NOI18N
        SMARTS.setToolTipText(org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.SMARTS.toolTipText")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(QUOTES, org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.QUOTES.text")); // NOI18N
        QUOTES.setToolTipText(org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.QUOTES.toolTipText")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(ABBREVIATIONS, org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.ABBREVIATIONS.text")); // NOI18N
        ABBREVIATIONS.setToolTipText(org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.ABBREVIATIONS.toolTipText")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(HARDWRAPS, org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.HARDWRAPS.text")); // NOI18N
        HARDWRAPS.setToolTipText(org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.HARDWRAPS.toolTipText")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(AUTOLINKS, org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.AUTOLINKS.text")); // NOI18N
        AUTOLINKS.setToolTipText(org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.AUTOLINKS.toolTipText")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(TABLES, org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.TABLES.text")); // NOI18N
        TABLES.setToolTipText(org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.TABLES.toolTipText")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(DEFINITION_LISTS, org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.DEFINITION_LISTS.text")); // NOI18N
        DEFINITION_LISTS.setToolTipText(org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.DEFINITION_LISTS.toolTipText")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(FENCED_CODE_BLOCKS, org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.FENCED_CODE_BLOCKS.text")); // NOI18N
        FENCED_CODE_BLOCKS.setToolTipText(org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.FENCED_CODE_BLOCKS.toolTipText")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(HTML_BLOCK_SUPPRESSION, org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.HTML_BLOCK_SUPPRESSION.text")); // NOI18N
        HTML_BLOCK_SUPPRESSION.setToolTipText(org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.HTML_BLOCK_SUPPRESSION.toolTipText")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(INLINE_HTML_SUPPRESSION, org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.INLINE_HTML_SUPPRESSION.text")); // NOI18N
        INLINE_HTML_SUPPRESSION.setToolTipText(org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.INLINE_HTML_SUPPRESSION.toolTipText")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(WIKILINKS, org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.WIKILINKS.text")); // NOI18N
        WIKILINKS.setToolTipText(org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.WIKILINKS.toolTipText")); // NOI18N

        javax.swing.GroupLayout EXTENSIONS_PANELLayout = new javax.swing.GroupLayout(EXTENSIONS_PANEL);
        EXTENSIONS_PANEL.setLayout(EXTENSIONS_PANELLayout);
        EXTENSIONS_PANELLayout.setHorizontalGroup(
            EXTENSIONS_PANELLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EXTENSIONS_PANELLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(EXTENSIONS_PANELLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EXTENSIONS_PANEL_HEADER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(EXTENSIONS_PANELLayout.createSequentialGroup()
                        .addGroup(EXTENSIONS_PANELLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(INLINE_HTML_SUPPRESSION)
                            .addComponent(SMARTS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(QUOTES, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ABBREVIATIONS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(HARDWRAPS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(AUTOLINKS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TABLES, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(DEFINITION_LISTS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(FENCED_CODE_BLOCKS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(HTML_BLOCK_SUPPRESSION, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(WIKILINKS, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        EXTENSIONS_PANELLayout.setVerticalGroup(
            EXTENSIONS_PANELLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EXTENSIONS_PANELLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(EXTENSIONS_PANEL_HEADER)
                .addGap(17, 17, 17)
                .addComponent(SMARTS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(QUOTES)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ABBREVIATIONS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(HARDWRAPS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(AUTOLINKS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TABLES)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(DEFINITION_LISTS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(FENCED_CODE_BLOCKS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(HTML_BLOCK_SUPPRESSION)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(INLINE_HTML_SUPPRESSION)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(WIKILINKS)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        TABS.addTab(org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.EXTENSIONS_PANEL.TabConstraints.tabTitle"), EXTENSIONS_PANEL); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(HTML_PANEL_HEADER, org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.HTML_PANEL_HEADER.text")); // NOI18N

        jScrollPane1.setViewportView(HTML_TEMPLATE);

        javax.swing.GroupLayout HTML_EXPORT_PANELLayout = new javax.swing.GroupLayout(HTML_EXPORT_PANEL);
        HTML_EXPORT_PANEL.setLayout(HTML_EXPORT_PANELLayout);
        HTML_EXPORT_PANELLayout.setHorizontalGroup(
            HTML_EXPORT_PANELLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HTML_EXPORT_PANELLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HTML_EXPORT_PANELLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(HTML_PANEL_HEADER, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                    .addGroup(HTML_EXPORT_PANELLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        HTML_EXPORT_PANELLayout.setVerticalGroup(
            HTML_EXPORT_PANELLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HTML_EXPORT_PANELLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(HTML_PANEL_HEADER)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                .addContainerGap())
        );

        TABS.addTab(org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.HTML_EXPORT_PANEL.TabConstraints.tabTitle"), HTML_EXPORT_PANEL); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(VIEW_HTML_ON_SAVE, org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.VIEW_HTML_ON_SAVE.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(SAVE_IN_SOURCE_DIR, org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.SAVE_IN_SOURCE_DIR.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(FX_HTML_VIEW_ENABLED, org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.FX_HTML_VIEW_ENABLED.text")); // NOI18N

        javax.swing.GroupLayout MISC_PANELLayout = new javax.swing.GroupLayout(MISC_PANEL);
        MISC_PANEL.setLayout(MISC_PANELLayout);
        MISC_PANELLayout.setHorizontalGroup(
            MISC_PANELLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MISC_PANELLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MISC_PANELLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FX_HTML_VIEW_ENABLED)
                    .addComponent(SAVE_IN_SOURCE_DIR)
                    .addComponent(VIEW_HTML_ON_SAVE))
                .addContainerGap(203, Short.MAX_VALUE))
        );
        MISC_PANELLayout.setVerticalGroup(
            MISC_PANELLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MISC_PANELLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(VIEW_HTML_ON_SAVE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SAVE_IN_SOURCE_DIR)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FX_HTML_VIEW_ENABLED)
                .addContainerGap(256, Short.MAX_VALUE))
        );

        TABS.addTab(org.openide.util.NbBundle.getMessage(MarkdownPanel.class, "MarkdownPanel.MISC_PANEL.TabConstraints.tabTitle"), MISC_PANEL); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(TABS)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(TABS)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void setMimeType(JEditorPane editorPane, String mimeType, String extension) {
        FileSystem fileSystem = FileUtil.createMemoryFileSystem();
        try {
            FileObject file = fileSystem.getRoot().createData("template", extension);
            DataObject data = DataObject.find(file);
            if (data != null) {
                EditorKit kit = CloneableEditorSupport.getEditorKit(mimeType);
                editorPane.setEditorKit(kit);
                editorPane.getDocument().putProperty(Document.StreamDescriptionProperty, data);
                DialogBinding.bindComponentToFile(file, 0, 0, editorPane);
                editorPane.setText(" ");
            }
        }
        catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private class ActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            controller.changed();
        }
    }

    private class DocumentHandler implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            controller.changed();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            controller.changed();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            controller.changed();
        }
    }

    void load() {
        // TODO read settings and initialize GUI
        // Example:
        // someCheckBox.setSelected(Preferences.userNodeForPackage(MarkdownPanel.class).getBoolean("someFlag", false));
        // or for org.openide.util with API spec. version >= 7.4:
        // someCheckBox.setSelected(NbPreferences.forModule(MarkdownPanel.class).getBoolean("someFlag", false));
        // or:
        // someTextField.setText(SomeSystemOption.getDefault().getSomeStringProperty());
        MarkdownGlobalOptions options = MarkdownGlobalOptions.getInstance();
        ABBREVIATIONS.setSelected(options.isAbbreviations());
        AUTOLINKS.setSelected(options.isAutoLinks());
        DEFINITION_LISTS.setSelected(options.isDefinitions());
        FENCED_CODE_BLOCKS.setSelected(options.isFencedCodeBlocks());
        HARDWRAPS.setSelected(options.isHardWraps());
        HTML_BLOCK_SUPPRESSION.setSelected(options.isSuppressHTMLBlocks());
        INLINE_HTML_SUPPRESSION.setSelected(options.isSuppressInlineHTML());
        QUOTES.setSelected(options.isQuotes());
        SMARTS.setSelected(options.isSmarts());
        TABLES.setSelected(options.isTables());
        WIKILINKS.setSelected(options.isWikiLinks());
        HTML_TEMPLATE.setText(options.getHtmlTemplate());
        VIEW_HTML_ON_SAVE.setSelected(options.isViewHtmlOnSave());
        SAVE_IN_SOURCE_DIR.setSelected(options.isSaveInSourceDir());
        FX_HTML_VIEW_ENABLED.setSelected(options.isFXHtmlViewEnabled());
    }

    void store() {
        // TODO store modified settings
        // Example:
        // Preferences.userNodeForPackage(MarkdownPanel.class).putBoolean("someFlag", someCheckBox.isSelected());
        // or for org.openide.util with API spec. version >= 7.4:
        // NbPreferences.forModule(MarkdownPanel.class).putBoolean("someFlag", someCheckBox.isSelected());
        // or:
        // SomeSystemOption.getDefault().setSomeStringProperty(someTextField.getText());

        MarkdownGlobalOptions options = MarkdownGlobalOptions.getInstance();
        options.setAbbreviations(ABBREVIATIONS.isSelected());
        options.setAutoLinks(AUTOLINKS.isSelected());
        options.setDefinitions(DEFINITION_LISTS.isSelected());
        options.setFencedCodeBlocks(FENCED_CODE_BLOCKS.isSelected());
        options.setHardWraps(HARDWRAPS.isSelected());
        options.setSuppressHTMLBlocks(HTML_BLOCK_SUPPRESSION.isSelected());
        options.setSuppressInlineHTML(INLINE_HTML_SUPPRESSION.isSelected());
        options.setQuotes(QUOTES.isSelected());
        options.setSmarts(SMARTS.isSelected());
        options.setTables(TABLES.isSelected());
        options.setWikiLinks(WIKILINKS.isSelected());
        options.setHtmlTemplate(HTML_TEMPLATE.getText());
        options.setViewHtmlOnSave(VIEW_HTML_ON_SAVE.isSelected());
        options.setSaveInSourceDir(SAVE_IN_SOURCE_DIR.isSelected());
        options.setFXHtmlViewEnabled(FX_HTML_VIEW_ENABLED.isSelected());
    }
    
    public static String getDefaultHtmlTemplate()
    {
        return "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\n"
                + "<title>{%TITLE%}</title>\n"
                + "<style type=\"text/css\">/*...*/</style>\n"
                + "</head>\n"
                + "<body>\n"
                + "{%CONTENT%}\n"
                + "</body>\n"
                + "</html>";
    }

    boolean valid() {
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox ABBREVIATIONS;
    private javax.swing.JCheckBox AUTOLINKS;
    private javax.swing.JCheckBox DEFINITION_LISTS;
    private javax.swing.JPanel EXTENSIONS_PANEL;
    private javax.swing.JLabel EXTENSIONS_PANEL_HEADER;
    private javax.swing.JCheckBox FENCED_CODE_BLOCKS;
    private javax.swing.JCheckBox FX_HTML_VIEW_ENABLED;
    private javax.swing.JCheckBox HARDWRAPS;
    private javax.swing.JCheckBox HTML_BLOCK_SUPPRESSION;
    private javax.swing.JPanel HTML_EXPORT_PANEL;
    private javax.swing.JLabel HTML_PANEL_HEADER;
    private javax.swing.JEditorPane HTML_TEMPLATE;
    private javax.swing.JCheckBox INLINE_HTML_SUPPRESSION;
    private javax.swing.JPanel MISC_PANEL;
    private javax.swing.JCheckBox QUOTES;
    private javax.swing.JCheckBox SAVE_IN_SOURCE_DIR;
    private javax.swing.JCheckBox SMARTS;
    private javax.swing.JCheckBox TABLES;
    private javax.swing.JTabbedPane TABS;
    private javax.swing.JCheckBox VIEW_HTML_ON_SAVE;
    private javax.swing.JCheckBox WIKILINKS;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
