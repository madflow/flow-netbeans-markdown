package flow.netbeans.markdown.highlighter;

public class MarkdownToken {

    private final MarkdownTokenId id;
    private final int startIndex;
    private final int endIndex;
    
    @Deprecated
    public MarkdownToken(MarkdownTokenId id, int length) {
        this(id, 0, length);
    }

    public MarkdownToken(MarkdownTokenId id, int startIndex, int endIndex) {
        this.id = id;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
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

    public int getLength() {
        return endIndex - startIndex;
    }

    @Override
    public String toString() {
        return id + "(" + getLength() + ")[" + startIndex + "-" + endIndex + "]";
    }
}
