
package flow.netbeans.markdown.matchers;

import flow.netbeans.markdown.highlighter.MarkdownToken;
import org.hamcrest.Description;
import org.junit.internal.matchers.TypeSafeMatcher;

/**
 *
 * @author Holger
 */
public class NonZeroLength extends TypeSafeMatcher<MarkdownToken> {

    public NonZeroLength() {
    }

    @Override
    public boolean matchesSafely(MarkdownToken item) {
        return item.getLength() > 0;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("length zero");
    }

}
