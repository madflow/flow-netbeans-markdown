package flow.netbeans.markdown.highlighter;

import org.netbeans.api.lexer.TokenId;

public enum MarkdownTokenId implements TokenId {

    HEADER("header"),
    LIST("list"),
    BLOCKQUOTE("blockquote"),
    REMOVED("removed"),
    PLAIN("plain"),
    BOLD("bold");
    
    private String name;

    MarkdownTokenId(
            String name) {
        this.name = name;
    }

    @Override
    public String primaryCategory() {
        return name;
    }
}
