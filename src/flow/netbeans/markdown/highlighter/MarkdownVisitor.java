package flow.netbeans.markdown.highlighter;

import org.pegdown.ast.*;
import static org.pegdown.ast.SimpleNode.Type.HRule;

public class MarkdownVisitor implements Visitor {
    
    private MarkdownTokenId currentToken = MarkdownTokenId.PLAIN;
    
    private final MarkdownTokenMap tokenMap = new MarkdownTokenMap();

    @Override
    public void visit(AbbreviationNode node) {
        addToken(MarkdownTokenId.ABBREVIATION, node);
    }

    @Override
    public void visit(AutoLinkNode node) {
        addToken(MarkdownTokenId.AUTOLINK, node);
    }

    @Override
    public void visit(BlockQuoteNode node) {
        addToken(MarkdownTokenId.BLOCKQUOTE, node);
    }

    @Override
    public void visit(CodeNode node) {
        addToken(MarkdownTokenId.CODE, node);
    }

    @Override
    public void visit(DefinitionListNode node) {
        addToken(MarkdownTokenId.DEFINITION_LIST, node);
    }

    @Override
    public void visit(DefinitionNode node) {
        addToken(MarkdownTokenId.DEFINITION, node);
    }

    @Override
    public void visit(DefinitionTermNode node) {
        addToken(MarkdownTokenId.DEFINITION_TERM, node);
    }

    @Override
    public void visit(ExpImageNode node) {
        addToken(MarkdownTokenId.EXPIMAGE, node);
    }

    @Override
    public void visit(ExpLinkNode node) {
        addToken(MarkdownTokenId.EXPLINK, node);
    }

    @Override
    public void visit(HeaderNode node) {
        addToken(MarkdownTokenId.HEADER, node);
    }

    @Override
    public void visit(HtmlBlockNode node) {
        addToken(MarkdownTokenId.HTMLBLOCK, node);
    }

    @Override
    public void visit(InlineHtmlNode node) {
        addToken(MarkdownTokenId.INLINEHTML, node);
    }

    @Override
    public void visit(ListItemNode node) {
        addToken(MarkdownTokenId.LISTITEM, node);
        //visitChildren(node);
    }

    @Override
    public void visit(OrderedListNode node) {
        addToken(MarkdownTokenId.ORDEREDLIST, node);
        //visitChildren(node);
    }
    
    @Override
    public void visit(BulletListNode node) {
        addToken(MarkdownTokenId.BULLETLIST, node);
        //visitChildren(node);
    }
    
    @Override
    public void visit(MailLinkNode node) {
        addToken(MarkdownTokenId.MAILLINK, node);
    }

    @Override
    public void visit(ParaNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(QuotedNode node) {
        addToken(MarkdownTokenId.QUOTED, node);
    }

    @Override
    public void visit(ReferenceNode node) {
        addToken(MarkdownTokenId.REFERENCE, node);
    }

    @Override
    public void visit(RefImageNode node) {
        addToken(MarkdownTokenId.REF_IMAGE, node);
    }

    @Override
    public void visit(RefLinkNode node) {
        addToken(MarkdownTokenId.REF_LINK, node);
    }

    @Override
    public void visit(SimpleNode node) {
        switch (node.getType()) {
            case HRule:
                addToken(MarkdownTokenId.HORIZONTALRULE, node);
                break;
        }
    }

    @Override
    public void visit(SpecialTextNode node) {
        
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
        visitChildren(node);
    }

    @Override
    public void visit(TableRowNode node) {
        
    }
    
    @Override
    public void visit(TableCaptionNode node) {

    }

    @Override
    public void visit(VerbatimNode node) {
        addToken(MarkdownTokenId.VERBATIM, node);
    }

    @Override
    public void visit(WikiLinkNode node) {
        currentToken = MarkdownTokenId.WIKILINK;
    }

    @Override
    public void visit(TextNode node) {

    }

    @Override
    public void visit(Node node) {
        addToken(MarkdownTokenId.PLAIN, node);
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
    public void visit(SuperNode node) {
        visitChildren(node);
    }

    protected void visitChildren(Node node) {
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

    @Override
    public void visit(StrongEmphSuperNode node) {
            addToken((node.isStrong() ? MarkdownTokenId.STRONG : MarkdownTokenId.EMPH), node);
            visitChildren(node);
    }

    private void addToken(MarkdownTokenId tokenId, Node node) {
        MarkdownToken tkn = new MarkdownToken(tokenId, 
                node.getEndIndex()-node.getStartIndex()
                );
        tokenMap.put(node.getStartIndex(), tkn);
        currentToken = tokenId;
    }
    
    public MarkdownTokenMap getTokenMap() {
        return tokenMap;
    }
    
}