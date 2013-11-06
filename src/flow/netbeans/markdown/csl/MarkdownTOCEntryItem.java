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
import org.pegdown.ast.HeaderNode;

/**
 *
 * @author Holger
 */
public class MarkdownTOCEntryItem implements StructureItem {
    private final FileObject file;
    private final HeaderNode node;

    private final String name;

    private final String sortText;

    private final int endIndex;

    private final int startIndex;

    private final List<MarkdownTOCEntryItem> nestedItems;

    public MarkdownTOCEntryItem(FileObject file, HeaderNode node, String sortText, int startIndex, int endIndex,
            List<MarkdownTOCEntryItem> nestedItems) {
        this.file = file;
        this.node = node;
        this.sortText = sortText;
        this.endIndex = endIndex;
        this.nestedItems = new ArrayList<MarkdownTOCEntryItem>(nestedItems);
        MarkdownInlineVisitor visitor = new MarkdownInlineVisitor();
        node.accept(visitor);
        this.name = visitor.getPlainText();
        this.startIndex = startIndex;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSortText() {
        return sortText;
    }

    @Override
    public String getHtml(HtmlFormatter formatter) {
        // TODO: Format embedded links or other markdown?
        formatter.appendText(getName());
        return formatter.getText();
    }

    @Override
    public ElementHandle getElementHandle() {
        // TODO: This method should not return null!
        return new MarkdownTOCEntryHandle(file, getName(), startIndex, endIndex);
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.TAG;
    }

    @Override
    public Set<Modifier> getModifiers() {
        return Collections.emptySet();
    }

    @Override
    public boolean isLeaf() {
        return nestedItems.isEmpty();
    }

    @Override
    public List<MarkdownTOCEntryItem> getNestedItems() {
        return Collections.unmodifiableList(nestedItems);
    }

    @Override
    public long getPosition() {
        return startIndex;
    }

    @Override
    public long getEndPosition() {
        return endIndex;
    }

    @Override
    public ImageIcon getCustomIcon() {
        return null;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.file != null ? this.file.hashCode() : 0);
        hash = 23 * hash + (this.name != null ? this.name.hashCode() : 0);
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
        final MarkdownTOCEntryItem other = (MarkdownTOCEntryItem) obj;
        if (this.file != other.file && (this.file == null || !this.file.equals(other.file))) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
