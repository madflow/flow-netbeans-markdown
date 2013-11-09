
package flow.netbeans.markdown.csl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.swing.ImageIcon;
import org.netbeans.modules.csl.api.ElementHandle;
import org.netbeans.modules.csl.api.ElementKind;
import org.netbeans.modules.csl.api.HtmlFormatter;
import org.netbeans.modules.csl.api.Modifier;
import org.netbeans.modules.csl.api.StructureItem;
import org.openide.filesystems.FileObject;
import org.openide.util.NbBundle;
import org.pegdown.ast.ReferenceNode;
import org.pegdown.ast.RootNode;

/**
 *
 * @author Holger
 */
@NbBundle.Messages({
    "TXT_MarkdownReferencesRootItem=References"
})
public class MarkdownReferencesRootItem implements StructureItem {
    private final FileObject file;

    private final RootNode node;
    private final List<MarkdownReferencesEntryItem> nestedItems;

    public MarkdownReferencesRootItem(FileObject file, RootNode node) {
        this.file = file;
        this.node = node;
        nestedItems = new ArrayList<MarkdownReferencesEntryItem>();
        for (ReferenceNode refNode : node.getReferences()) {
            nestedItems.add(new MarkdownReferencesEntryItem(file, refNode));
        }
    }

    @Override
    public String getName() {
        return Bundle.TXT_MarkdownReferencesRootItem();
    }

    @Override
    public String getSortText() {
        return "2References";
    }

    @Override
    public String getHtml(HtmlFormatter formatter) {
        formatter.appendText(getName());
        return formatter.getText();
    }

    @Override
    public ElementHandle getElementHandle() {
        return null;
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.PACKAGE;
    }

    @Override
    public Set<Modifier> getModifiers() {
        return Collections.emptySet();
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public List<? extends StructureItem> getNestedItems() {
        return Collections.unmodifiableList(nestedItems);
    }

    @Override
    public long getPosition() {
        return 0;
    }

    @Override
    public long getEndPosition() {
        return 0;
    }

    @Override
    public ImageIcon getCustomIcon() {
        return null;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + (this.file != null ? this.file.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MarkdownReferencesRootItem other = (MarkdownReferencesRootItem) obj;
        if (this.file != other.file && (this.file == null || !this.file.equals(other.file))) {
            return false;
        }
        return true;
    }
}
