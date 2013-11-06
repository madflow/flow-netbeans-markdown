
package flow.netbeans.markdown.csl;

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
public class MarkdownInlineVisitor implements Visitor {
    final StringBuilder plainText;

    public MarkdownInlineVisitor() {
        plainText = new StringBuilder();
    }

    public String getPlainText() {
        return plainText.toString();
    }

    @Override
    public void visit(AbbreviationNode node) {
    }

    @Override
    public void visit(AutoLinkNode node) {
        plainText.append(node.getText());
    }

    @Override
    public void visit(BlockQuoteNode node) {
    }

    @Override
    public void visit(BulletListNode node) {
    }

    @Override
    public void visit(CodeNode node) {
        plainText.append(node.getText());
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
        visitChildren(node);
    }

    @Override
    public void visit(ExpLinkNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(HeaderNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(HtmlBlockNode node) {
    }

    @Override
    public void visit(InlineHtmlNode node) {
        plainText.append(node.getText());
    }

    @Override
    public void visit(ListItemNode node) {
    }

    @Override
    public void visit(MailLinkNode node) {
        plainText.append(node.getText());
    }

    @Override
    public void visit(OrderedListNode node) {
    }

    @Override
    public void visit(ParaNode node) {
    }

    @Override
    public void visit(QuotedNode node) {
        switch (node.getType()) {
            case DoubleAngle:
            case Double:
                plainText.append('"');
                break;
            case Single:
                plainText.append('\'');
                break;
        }
        visitChildren(node);
        switch (node.getType()) {
            case DoubleAngle:
            case Double:
                plainText.append('"');
                break;
            case Single:
                plainText.append('\'');
                break;
        }
    }

    @Override
    public void visit(ReferenceNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(RefImageNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(RefLinkNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(RootNode node) {
    }

    @Override
    public void visit(SimpleNode node) {
        switch (node.getType()) {
            case Apostrophe:
                plainText.append('\'');
                break;
            case Ellipsis:
                plainText.append("...");
                break;
            case Emdash:
            case Endash:
                plainText.append('-');
                break;
            case Nbsp:
                plainText.append(' ');
                break;
            case HRule:
            case Linebreak:
                // Ignore.
                break;
        }
    }

    @Override
    public void visit(SpecialTextNode node) {
        plainText.append(node.getText());
    }

    @Override
    public void visit(StrongEmphSuperNode node) {
        visitChildren(node);
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
        plainText.append(node.getText());
    }

    @Override
    public void visit(TextNode node) {
        plainText.append(node.getText());
    }

    @Override
    public void visit(SuperNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(Node node) {
        visitChildren(node);
    }

    protected void visitChildren(Node node) {
        for (Node child : node.getChildren()) {
            child.accept(this);
        }
    }
}
