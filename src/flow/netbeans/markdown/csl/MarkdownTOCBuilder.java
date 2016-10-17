package flow.netbeans.markdown.csl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.csl.api.StructureItem;
import org.openide.filesystems.FileObject;
import org.pegdown.ast.HeaderNode;
import org.pegdown.ast.Node;

/**
 *
 * @author Holger
 */
public class MarkdownTOCBuilder {
    private final Entry rootEntry;

    private Entry currentEntry;
    private final FileObject file;

    public MarkdownTOCBuilder(FileObject file) {
        rootEntry = new Entry();
        currentEntry = rootEntry;
        this.file = file;
    }

    public void add(HeaderNode node) {
        if (currentEntry == rootEntry || node.getLevel() > currentEntry.node.getLevel()) {
            currentEntry = currentEntry.addChild(node);
        } else {
            currentEntry.finish(node.getStartIndex() - 1);
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

    public List<OffsetRange> buildOffsetRanges() {
        final List<OffsetRange> ranges = new ArrayList<OffsetRange>();
        rootEntry.buildNestedOffsetRanges(ranges);
        return ranges;
    }

    private class Entry {
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
            return new MarkdownTOCEntryItem(file, node, buildCounterPath(), startIndex, endIndex, buildNestedItems());
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
                sb.append(String.format(Locale.US, getCounterFormat(), counter));
            }
        }
        
        private String getCounterFormat() {
            String counterFormat = "%d";
            if (parent != null) {
                final int counterWidth = Integer.toString(parent.children.size()).length();
                 counterFormat = "%0" + counterWidth + "d";
            }
            return counterFormat;
        }

        private void finish(int endIndex) {
            this.endIndex = endIndex;
            if (parent != null) {
                parent.finish(endIndex);
            }
        }

        private void buildNestedOffsetRanges(List<OffsetRange> ranges) {
            for (Entry child : children) {
                child.buildOffsetRanges(ranges);
            }
        }

        private void buildOffsetRanges(List<OffsetRange> ranges) {
            List<Node> childNodes = node.getChildren();
            if (!childNodes.isEmpty()) {
                int childrenEndIndex = childNodes.get(childNodes.size() - 1).getEndIndex();
                if (endIndex > childrenEndIndex) {
                    ranges.add(new OffsetRange(childrenEndIndex, endIndex));
                }
            }
            buildNestedOffsetRanges(ranges);
        }
    }
}
