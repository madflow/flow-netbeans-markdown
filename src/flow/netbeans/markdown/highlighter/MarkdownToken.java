package flow.netbeans.markdown.highlighter;

public class MarkdownToken {

    private MarkdownTokenId id;
    private int length;
    
    public MarkdownToken(MarkdownTokenId id, int length) {
        this.id = id;
        this.length = length;
    }

    public MarkdownTokenId getId() {
        return id;
    }

    public int getLength() {
        return length;
    }
}
