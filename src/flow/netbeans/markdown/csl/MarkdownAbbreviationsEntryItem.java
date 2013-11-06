package flow.netbeans.markdown.csl;

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
import org.pegdown.ast.AbbreviationNode;

/**
 *
 * @author Holger
 */
public class MarkdownAbbreviationsEntryItem implements StructureItem {
    private final FileObject file;

    private final AbbreviationNode node;

    private final String name;

    private final String expansion;

    public MarkdownAbbreviationsEntryItem(FileObject file, AbbreviationNode node) {
        this.file = file;
        this.node = node;
        MarkdownInlineVisitor visitor = new MarkdownInlineVisitor();
        node.accept(visitor);
        this.name = visitor.getPlainText();
        if (node.getExpansion() != null) {
            visitor = new MarkdownInlineVisitor();
            node.getExpansion().accept(visitor);
            this.expansion = visitor.getPlainText().trim();
        }
        else {
            this.expansion = "";
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSortText() {
        return getName();
    }

    @Override
    public String getHtml(HtmlFormatter formatter) {
        formatter.appendText(getName());
        if (!expansion.isEmpty()) {
            formatter.appendHtml(" <font color='!controlShadow'>");
            formatter.appendText(expansion);
            formatter.appendHtml("</font>");
        }
        return formatter.getText();
    }

    @Override
    public ElementHandle getElementHandle() {
        return null;
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.CONSTANT;
    }

    @Override
    public Set<Modifier> getModifiers() {
        return Collections.emptySet();
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public List<? extends StructureItem> getNestedItems() {
        return Collections.emptyList();
    }

    @Override
    public long getPosition() {
        return node.getStartIndex();
    }

    @Override
    public long getEndPosition() {
        return node.getEndIndex();
    }

    @Override
    public ImageIcon getCustomIcon() {
        return null;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.file != null ? this.file.hashCode() : 0);
        hash = 29 * hash + (this.name != null ? this.name.hashCode() : 0);
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
        final MarkdownAbbreviationsEntryItem other = (MarkdownAbbreviationsEntryItem) obj;
        if (this.file != other.file && (this.file == null || !this.file.equals(other.file))) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
