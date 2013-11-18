package flow.netbeans.markdown.highlighter;

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
public class MarkdownLexerVisitor implements Visitor {
    private final MarkdownTokenListBuilder builder;

    public MarkdownLexerVisitor(MarkdownTokenListBuilder builder) {
        this.builder = builder;
    }

    private void addLeafTreeToken(MarkdownTokenId id, Node node) {
        builder.addLeafTreeToken(id, node.getStartIndex(), node.getEndIndex());
    }

    private void beginTreeToken(MarkdownTokenId id, Node node) {
        builder.beginTreeToken(id, node.getStartIndex(), node.getEndIndex());
    }

    private void endTreeToken() {
        builder.endTreeToken();
    }

    private void visitChildren(SuperNode node) {
        for (Node child : node.getChildren()) {
            child.accept(this);
        }
    }

    private void visitNode(Node node) {
        if (node != null) {
            node.accept(this);
        }
    }

    @Override
    public void visit(RootNode node) {
        beginTreeToken(MarkdownTokenId.PLAIN, node);
        visitChildren(node);
        endTreeToken();
    }

    @Override
    public void visit(AbbreviationNode node) {
        beginTreeToken(MarkdownTokenId.ABBREVIATION, node);
        visitChildren(node);
        visitNode(node.getExpansion());
        endTreeToken();
    }

    @Override
    public void visit(AutoLinkNode node) {
        addLeafTreeToken(MarkdownTokenId.AUTOLINK, node);
    }

    @Override
    public void visit(BlockQuoteNode node) {
        beginTreeToken(MarkdownTokenId.BLOCKQUOTE, node);
        visitChildren(node);
        endTreeToken();
    }

    @Override
    public void visit(BulletListNode node) {
        beginTreeToken(MarkdownTokenId.BULLETLIST, node);
        visitChildren(node);
        endTreeToken();
    }

    @Override
    public void visit(CodeNode node) {
        addLeafTreeToken(MarkdownTokenId.CODE, node);
    }

    @Override
    public void visit(DefinitionListNode node) {
        beginTreeToken(MarkdownTokenId.DEFINITION_LIST, node);
        visitChildren(node);
        endTreeToken();
    }

    @Override
    public void visit(DefinitionNode node) {
        beginTreeToken(MarkdownTokenId.DEFINITION, node);
        visitChildren(node);
        endTreeToken();
    }

    @Override
    public void visit(DefinitionTermNode node) {
        beginTreeToken(MarkdownTokenId.DEFINITION_TERM, node);
        visitChildren(node);
        endTreeToken();
    }

    @Override
    public void visit(ExpImageNode node) {
        beginTreeToken(MarkdownTokenId.EXPIMAGE, node);
        visitChildren(node);
        endTreeToken();
    }

    @Override
    public void visit(ExpLinkNode node) {
        beginTreeToken(MarkdownTokenId.EXPLINK, node);
        visitChildren(node);
        endTreeToken();
    }

    @Override
    public void visit(HeaderNode node) {
        switch (node.getLevel()) {
            case 1:
                beginTreeToken(MarkdownTokenId.HEADER1, node);
                break;
            case 2:
                beginTreeToken(MarkdownTokenId.HEADER2, node);
                break;
            case 3:
                beginTreeToken(MarkdownTokenId.HEADER3, node);
                break;
            case 4:
                beginTreeToken(MarkdownTokenId.HEADER4, node);
                break;
            case 5:
                beginTreeToken(MarkdownTokenId.HEADER5, node);
                break;
            case 6:
                beginTreeToken(MarkdownTokenId.HEADER6, node);
                break;
            default:
                beginTreeToken(MarkdownTokenId.PLAIN, node);
        }
        visitChildren(node);
        endTreeToken();
    }

    @Override
    public void visit(HtmlBlockNode node) {
        addLeafTreeToken(MarkdownTokenId.HTMLBLOCK, node);
    }

    @Override
    public void visit(InlineHtmlNode node) {
        addLeafTreeToken(MarkdownTokenId.INLINEHTML, node);
    }

    @Override
    public void visit(ListItemNode node) {
        addLeafTreeToken(MarkdownTokenId.LISTITEM, node);
        visitChildren(node);
        endTreeToken();
    }

    @Override
    public void visit(MailLinkNode node) {
        addLeafTreeToken(MarkdownTokenId.MAILLINK, node);
    }

    @Override
    public void visit(OrderedListNode node) {
        beginTreeToken(MarkdownTokenId.ORDEREDLIST, node);
        visitChildren(node);
        endTreeToken();
    }

    @Override
    public void visit(ParaNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(QuotedNode node) {
        beginTreeToken(MarkdownTokenId.QUOTED, node);
        visitChildren(node);
        endTreeToken();
    }

    @Override
    public void visit(ReferenceNode node) {
        beginTreeToken(MarkdownTokenId.REFERENCE, node);
        visitChildren(node);
        endTreeToken();
    }

    @Override
    public void visit(RefImageNode node) {
        beginTreeToken(MarkdownTokenId.REF_IMAGE, node);
        visitChildren(node);
        endTreeToken();
    }

    @Override
    public void visit(RefLinkNode node) {
        beginTreeToken(MarkdownTokenId.REF_LINK, node);
        visitChildren(node);
        endTreeToken();
    }

    @Override
    public void visit(SimpleNode node) {
        switch (node.getType()) {
            case HRule:
                addLeafTreeToken(MarkdownTokenId.HORIZONTALRULE, node);
                break;
            case Apostrophe:
            case Ellipsis:
            case Emdash:
            case Endash:
            case Linebreak:
            case Nbsp:
                break;
        }
    }

    @Override
    public void visit(SpecialTextNode node) {
        //addLeafTreeToken(MarkdownTokenId.TEXT, node);
    }

    @Override
    public void visit(StrongEmphSuperNode node) {
        beginTreeToken(node.isStrong() ? MarkdownTokenId.STRONG : MarkdownTokenId.EMPH, node);
        visitChildren(node);
        endTreeToken();
    }

    @Override
    public void visit(TableBodyNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(TableCaptionNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(TableCellNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(TableColumnNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(TableHeaderNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(TableNode node) {
        beginTreeToken(MarkdownTokenId.TABLE, node);
        visitChildren(node);
        endTreeToken();
    }

    @Override
    public void visit(TableRowNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(VerbatimNode node) {
        addLeafTreeToken(MarkdownTokenId.VERBATIM, node);
    }

    @Override
    public void visit(WikiLinkNode node) {
        addLeafTreeToken(MarkdownTokenId.WIKILINK, node);
    }

    @Override
    public void visit(TextNode node) {
//        addLeafTreeToken(MarkdownTokenId.TEXT, node);
    }

    @Override
    public void visit(SuperNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(Node node) {
        addLeafTreeToken(MarkdownTokenId.PLAIN, node);
    }

    protected static class MarkdownTreeToken {
        private final MarkdownTokenId id;

        private final int startIndex;

        private final int endIndex;

        private final int depth;

        private int remainderIndex;

        public MarkdownTreeToken(MarkdownTokenId id, int depth, int startIndex, int endIndex) {
            this.id = id;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.depth = depth;
            this.remainderIndex = startIndex;
        }

        public MarkdownTokenId getId() {
            return id;
        }

        public int getStartIndex() {
            return startIndex;
        }

        public int getEndIndex() {
            return endIndex;
        }

        public int getDepth() {
            return depth;
        }

        public int getRemainderIndex() {
            return remainderIndex;
        }

        public void setRemainderIndex(int remainderIndex) {
            this.remainderIndex = remainderIndex;
        }

        @Override
        public String toString() {
            return id + "[" + startIndex + "-" + endIndex + "@" + remainderIndex + "]";
        }
    }
}
