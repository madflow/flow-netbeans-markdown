package flow.netbeans.markdown.highlighter;

import org.pegdown.ast.*;

public class MarkdownVisitor implements Visitor {
    
    private MarkdownTokenId currentToken = MarkdownTokenId.PLAIN;

    @Override
    public void visit(AbbreviationNode node) {
        
    }

    @Override
    public void visit(AutoLinkNode node) {
        
    }

    @Override
    public void visit(BlockQuoteNode node) {
        currentToken = MarkdownTokenId.BLOCKQUOTE;
    }

    @Override
    public void visit(BulletListNode node) {
        currentToken = MarkdownTokenId.BULLETLIST;
    }

    @Override
    public void visit(CodeNode node) {
        currentToken = MarkdownTokenId.CODE;
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
    public void visit(EmphNode node) {
       
    }

    @Override
    public void visit(ExpImageNode node) {
        
    }

    @Override
    public void visit(ExpLinkNode node) {
        
    }

    @Override
    public void visit(HeaderNode node) {
        currentToken = MarkdownTokenId.HEADER;
    }

    @Override
    public void visit(HtmlBlockNode node) {
        currentToken = MarkdownTokenId.HTMLBLOCK;
    }

    @Override
    public void visit(InlineHtmlNode node) {
        
    }

    @Override
    public void visit(ListItemNode node) {
        currentToken = MarkdownTokenId.LISTITEM;
    }

    @Override
    public void visit(MailLinkNode node) {
        
    }

    @Override
    public void visit(OrderedListNode node) {
        currentToken = MarkdownTokenId.ORDEREDLIST;
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
        for (AbbreviationNode abbreviationNode : node.getAbbreviations()) {
            abbreviationNode.accept(this);
        }
            for (ReferenceNode referenceNode : node.getReferences()) {
            referenceNode.accept(this);
        }
            visitChildren(node);
    }

    @Override
    public void visit(SimpleNode node) {
        switch (node.getType()) {
            case HRule:
                currentToken = MarkdownTokenId.HORIZONTALRULE;
                break;
        }
    }

    @Override
    public void visit(SpecialTextNode node) {
        
    }

    @Override
    public void visit(StrongNode node) {
        
    }

    @Override
    public void visit(TableBodyNode node) {
        
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
        currentToken = MarkdownTokenId.BLOCKQUOTE;
    }

    @Override
    public void visit(WikiLinkNode node) {
        currentToken = MarkdownTokenId.WIKILINK;
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
    public void visit(TableCaptionNode node) {

    }
    
    protected void visitChildren(SuperNode node) {
        for (Node child : node.getChildren()) {
            child.accept(this);
        }
    }
    
    public MarkdownTokenId getCurrentToken()
    {
        MarkdownTokenId tk = currentToken;
        currentToken = MarkdownTokenId.PLAIN;
        return tk;
    }
}