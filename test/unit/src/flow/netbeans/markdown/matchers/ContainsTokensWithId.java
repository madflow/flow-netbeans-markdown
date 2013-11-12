
package flow.netbeans.markdown.matchers;

import flow.netbeans.markdown.highlighter.MarkdownToken;
import flow.netbeans.markdown.highlighter.MarkdownTokenId;
import java.util.Iterator;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 *
 * @author Holger
 */
class ContainsTokensWithId extends BaseMatcher<Iterable<MarkdownToken>> {
    private final MarkdownTokenId[] ids;

    public ContainsTokensWithId(MarkdownTokenId... ids) {
        this.ids = ids.clone();
    }

    @Override
    public boolean matches(Object item) {
        Iterable<MarkdownToken> tokens = (Iterable<MarkdownToken>) item;
        Iterator<MarkdownToken> iterator = tokens.iterator();
        boolean result = true;
        int index;
        for (index = 0; index < ids.length && iterator.hasNext() && result; ++index) {
            MarkdownToken token = iterator.next();
            if (!token.getId().equals(ids[index])) {
                return false;
            }
        }
        if (index < ids.length || iterator.hasNext()) {
            return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("token ids not equal to ");
        description.appendValueList("", ", ", "", ids);
    }

}
