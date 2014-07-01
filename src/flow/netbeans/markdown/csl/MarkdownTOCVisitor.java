
package flow.netbeans.markdown.csl;

import java.util.List;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.csl.api.StructureItem;
import org.openide.filesystems.FileObject;
import org.pegdown.ast.AbbreviationNode;
import org.pegdown.ast.AutoLinkNode;
import org.pegdown.ast.BlockQuoteNode;
import org.pegdown.ast.BulletListNode;
import org.pegdown.ast.CodeNode;
import org.pegdown.ast.DefinitionListNode;
import org.pegdown.ast.DefinitionNode;
import org.pegdown.ast.DefinitionTermNode;
import org.pegdown.ast.ExpImageNode;
import org.pegdown.ast.ExpLinkNode;
import org.pegdown.ast.HeaderNode;
import org.pegdown.ast.HtmlBlockNode;
import org.pegdown.ast.InlineHtmlNode;
import org.pegdown.ast.ListItemNode;
import org.pegdown.ast.MailLinkNode;
import org.pegdown.ast.Node;
import org.pegdown.ast.OrderedListNode;
import org.pegdown.ast.ParaNode;
import org.pegdown.ast.QuotedNode;
import org.pegdown.ast.RefImageNode;
import org.pegdown.ast.RefLinkNode;
import org.pegdown.ast.ReferenceNode;
import org.pegdown.ast.RootNode;
import org.pegdown.ast.SimpleNode;
import org.pegdown.ast.SpecialTextNode;
import org.pegdown.ast.StrikeNode;
import org.pegdown.ast.StrongEmphSuperNode;
import org.pegdown.ast.SuperNode;
import org.pegdown.ast.TableBodyNode;
import org.pegdown.ast.TableCaptionNode;
import org.pegdown.ast.TableCellNode;
import org.pegdown.ast.TableColumnNode;
import org.pegdown.ast.TableHeaderNode;
import org.pegdown.ast.TableNode;
import org.pegdown.ast.TableRowNode;
import org.pegdown.ast.TextNode;
import org.pegdown.ast.VerbatimNode;
import org.pegdown.ast.Visitor;
import org.pegdown.ast.WikiLinkNode;

/**
 *
 * @author Holger
 */
public class MarkdownTOCVisitor implements Visitor {
    private final MarkdownTOCBuilder tocBuilder;

    public MarkdownTOCVisitor(FileObject file) {
        tocBuilder = new MarkdownTOCBuilder(file);
    }

    public List<? extends StructureItem> getTOCEntryItems() {
        return tocBuilder.build();
    }

    public List<OffsetRange> getOffsetRanges() {
        return tocBuilder.buildOffsetRanges();
    }

    @Override
    public void visit(AbbreviationNode node) {
    }

    @Override
    public void visit(AutoLinkNode node) {
    }

    @Override
    public void visit(BlockQuoteNode node) {
    }

    @Override
    public void visit(BulletListNode node) {
    }

    @Override
    public void visit(CodeNode node) {
    }

    @Override
    public void visit(DefinitionListNode node) {
    }

    @Override
    public void visit(DefinitionNode node) {
    }

    @Override
    public void visit(DefinitionTermNode node) {
    }

    @Override
    public void visit(ExpImageNode node) {
    }

    @Override
    public void visit(ExpLinkNode node) {
    }

    @Override
    public void visit(HeaderNode node) {
        tocBuilder.add(node);
    }

    @Override
    public void visit(HtmlBlockNode node) {
    }

    @Override
    public void visit(InlineHtmlNode node) {
    }

    @Override
    public void visit(ListItemNode node) {
    }

    @Override
    public void visit(MailLinkNode node) {
    }

    @Override
    public void visit(OrderedListNode node) {
    }

    @Override
    public void visit(ParaNode node) {
    }

    @Override
    public void visit(QuotedNode node) {
    }

    @Override
    public void visit(ReferenceNode node) {
    }

    @Override
    public void visit(RefImageNode node) {
    }

    @Override
    public void visit(RefLinkNode node) {
    }

    @Override
    public void visit(RootNode node) {
        for (Node child : node.getChildren()) {
            child.accept(this);
        }
        tocBuilder.finish(node.getEndIndex());
    }

    @Override
    public void visit(SimpleNode node) {
    }

    @Override
    public void visit(SpecialTextNode node) {
    }

    @Override
    public void visit(StrongEmphSuperNode node) {
    }

    @Override
    public void visit(TableBodyNode node) {
    }

    @Override
    public void visit(TableCaptionNode node) {
    }

    @Override
    public void visit(TableCellNode node) {
    }

    @Override
    public void visit(TableColumnNode node) {
    }

    @Override
    public void visit(TableHeaderNode node) {
    }

    @Override
    public void visit(TableNode node) {
    }

    @Override
    public void visit(TableRowNode node) {
    }

    @Override
    public void visit(VerbatimNode node) {
    }

    @Override
    public void visit(WikiLinkNode node) {
    }

    @Override
    public void visit(TextNode node) {
    }

    @Override
    public void visit(SuperNode node) {
    }

    @Override
    public void visit(Node node) {
    }

    @Override
    public void visit(StrikeNode node) {
    }
}
