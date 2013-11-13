
package flow.netbeans.markdown.matchers;

import flow.netbeans.markdown.highlighter.MarkdownToken;
import java.util.Arrays;
import java.util.Iterator;
import org.hamcrest.Description;
import org.junit.internal.matchers.TypeSafeMatcher;

/**
 *
 * @author Holger
 */
class ContainsTokensWithLength extends TypeSafeMatcher<Iterable<MarkdownToken>> {
    private final int[] lengths;

    public ContainsTokensWithLength(int... lengths) {
        this.lengths = lengths.clone();
    }

    @Override
    public boolean matchesSafely(Iterable<MarkdownToken> item) {
        Iterator<MarkdownToken> iterator = item.iterator();
        boolean result = true;
        int index;
        for (index = 0; index < lengths.length && iterator.hasNext() && result; ++index) {
            MarkdownToken token = iterator.next();
            if (token.getLength() != lengths[index]) {
                return false;
            }
        }
        if (index < lengths.length || iterator.hasNext()) {
            return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("token lengths not equal to ");
        description.appendValueList("", ", ", "", Arrays.asList(lengths));
    }

}
