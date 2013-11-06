package flow.netbeans.markdown.csl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.netbeans.modules.csl.api.StructureItem;
import org.pegdown.ast.HeaderNode;

/**
 *
 * @author Holger
 */
public class MarkdownTOCBuilder {
    private final Entry rootEntry;

    private Entry currentEntry;

    public MarkdownTOCBuilder() {
        rootEntry = new Entry();
        currentEntry = rootEntry;
    }

    public void add(HeaderNode node) {
        if (currentEntry == rootEntry || node.getLevel() > currentEntry.node.getLevel()) {
            currentEntry = currentEntry.addChild(node);
        } else {
            currentEntry.finish(node.getStartIndex());
            while (currentEntry != rootEntry && node.getLevel() <= currentEntry.node.getLevel()) {
                currentEntry = currentEntry.parent;
            }
            currentEntry = currentEntry.addChild(node);
        }
    }

    public void finish(int endIndex) {
        currentEntry.finish(endIndex);
    }

    public List<? extends StructureItem> build() {
        return rootEntry.buildNestedItems();
    }

    private static class Entry {
        private final Entry parent;

        private final int depth;

        private final int counter;

        private final HeaderNode node;

        private final List<Entry> children;

        private final int startIndex;

        private int endIndex;

        public Entry() {
            this(null, null, 0, 0);
        }

        public Entry(Entry parent, HeaderNode node, int depth, int counter) {
            this.parent = parent;
            this.depth = depth;
            this.node = node;
            this.counter = counter;
            this.children = new ArrayList<Entry>();
            this.startIndex = (node == null) ? 0 : node.getStartIndex();
            this.endIndex = (node == null) ? 0 : node.getEndIndex();
        }

        private Entry addChild(HeaderNode node) {
            Entry child = new Entry(this, node, depth+1, children.size()+1);
            children.add(child);
            return child;
        }

        private List<MarkdownTOCEntryItem> buildNestedItems() {
            final List<MarkdownTOCEntryItem> nestedItems;
            if (children.isEmpty()) {
                nestedItems = Collections.emptyList();
            }
            else {
                nestedItems = new ArrayList<MarkdownTOCEntryItem>(children.size());
                for (Entry child : children) {
                    nestedItems.add(child.build());
                }
            }
            return nestedItems;
        }

        private MarkdownTOCEntryItem build() {
            return new MarkdownTOCEntryItem(node, buildCounterPath(), startIndex, endIndex, buildNestedItems());
        }

        private String buildCounterPath() {
            StringBuilder sb = new StringBuilder();
            buildCounterPath(sb);
            return sb.toString();
        }

        private void buildCounterPath(StringBuilder sb) {
            if (parent != null) {
                parent.buildCounterPath(sb);
            }
            if (depth > 0) {
                if (sb.length() > 0) {
                    sb.append('.');
                }
                sb.append(counter);
            }
        }

        private void finish(int endIndex) {
            this.endIndex = endIndex;
            if (parent != null) {
                parent.finish(endIndex);
            }
        }
    }
}
