
package flow.netbeans.markdown.matchers;

import flow.netbeans.markdown.highlighter.MarkdownToken;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 *
 * @author Holger
 */
class NonZeroLength extends BaseMatcher<MarkdownToken> {

    public NonZeroLength() {
    }

    @Override
    public boolean matches(Object item) {
        MarkdownToken token = (MarkdownToken) item;
        return token.getLength() > 0;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("length zero");
    }

}
