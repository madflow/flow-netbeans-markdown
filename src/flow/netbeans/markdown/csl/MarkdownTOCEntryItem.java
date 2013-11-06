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
import org.pegdown.ast.HeaderNode;

/**
 *
 * @author Holger
 */
public class MarkdownTOCEntryItem implements StructureItem {
    private final HeaderNode node;

    private final String name;

    private final String sortText;

    private final int endIndex;

    private final int startIndex;

    private final List<MarkdownTOCEntryItem> nestedItems;

    public MarkdownTOCEntryItem(HeaderNode node, String sortText, int startIndex, int endIndex,
            List<MarkdownTOCEntryItem> nestedItems) {
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
        return new MarkdownTOCEntryHandle(getName(), node.getStartIndex(), node.getEndIndex());
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
}
