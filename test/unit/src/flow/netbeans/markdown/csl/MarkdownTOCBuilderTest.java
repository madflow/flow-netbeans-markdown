package flow.netbeans.markdown.csl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.modules.csl.api.StructureItem;
import org.pegdown.ast.HeaderNode;
import org.pegdown.ast.TextNode;

public class MarkdownTOCBuilderTest {

    private static final Comparator<StructureItem> BY_SORT_KEY = new Comparator<StructureItem>() {

        @Override
        public int compare(StructureItem o1, StructureItem o2) {
            return o1.getSortText().compareTo(o2.getSortText());
        }

    };

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test fix for <a href="https://github.com/madflow/flow-netbeans-markdown/issues/114">issue #114</a>.
     */
    @Test
    public void testSortKeyReflectsDocumentStructure() {
        // given
        MarkdownTOCBuilder builder = new MarkdownTOCBuilder(null);
        final int count = 20;
        List<HeaderNode> nodes = createHeaderNodes(count);

        // when
        for (HeaderNode node : nodes) {
            builder.add(node);
        }
        builder.finish(count * 100);
        List<? extends StructureItem> items = builder.build();

        // then
        List<? extends StructureItem> expItems = new ArrayList<StructureItem>(items);
        Collections.sort(expItems, BY_SORT_KEY);
        
        Assert.assertEquals("items are sorted by sort key", expItems, items);
    }

    private List<HeaderNode> createHeaderNodes(int count) {
        final List<HeaderNode> headerNodes = new ArrayList<HeaderNode>();
        for (int index = 0; index < count; ++index) {
            headerNodes.add(createHeaderNode(index));
        }

        return headerNodes;
    }

    private HeaderNode createHeaderNode(int index) {
        TextNode textNode = new TextNode("Heading " + (index + 1));

        HeaderNode headerNode = new HeaderNode(1, textNode);

        headerNode.setStartIndex(index * 100);
        textNode.setStartIndex(headerNode.getStartIndex() + 2);
        textNode.setEndIndex(textNode.getStartIndex() + textNode.getText().length());
        headerNode.setEndIndex(textNode.getEndIndex());

        return headerNode;
    }
}
