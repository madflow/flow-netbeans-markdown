
package flow.netbeans.markdown.matchers;

import flow.netbeans.markdown.highlighter.MarkdownToken;
import flow.netbeans.markdown.highlighter.MarkdownTokenId;
import org.hamcrest.Matcher;

/**
 *
 * @author Holger
 */
public class Matchers {
    private Matchers() {}

    public static <T> Matcher<Iterable<? extends T>> everyItem(Matcher<T> itemMatcher) {
        return new EveryItem<T>(itemMatcher);
    }

    public static Matcher<MarkdownToken> nonZeroLength() {
        return new NonZeroLength();
    }

    public static int totalLengthOf(Iterable<? extends MarkdownToken> iterable) {
        int totalLength = 0;
        for (MarkdownToken token : iterable) {
            totalLength += token.getLength();
        }
        return totalLength;
    }

    public static Matcher<Iterable<MarkdownToken>> containsTokensWithId(final MarkdownTokenId... ids) {
        return new ContainsTokensWithId(ids);
    }

    public static Matcher<Iterable<MarkdownToken>> containsTokensWithLength(final int... lengths) {
        return new ContainsTokensWithLength(lengths);
    }
}
