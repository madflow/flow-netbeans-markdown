
package flow.netbeans.markdown.highlighter;

import java.util.TreeMap;

public class MarkdownTokenMap extends TreeMap {

    private int totalTokenLength = 0;

    public MarkdownTokenMap() {
        this.totalTokenLength = 0;
    }
    
    public Object put(Number key, MarkdownToken token) {
        this.totalTokenLength += token.getLength();
        return super.put(key, token);
    }
    
    public int getTotalTokenLength() {
        return totalTokenLength;
    }
}
