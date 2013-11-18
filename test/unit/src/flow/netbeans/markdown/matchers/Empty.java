package flow.netbeans.markdown.matchers;

import java.util.Collection;
import org.hamcrest.Description;
import org.junit.internal.matchers.TypeSafeMatcher;

/**
 *
 * @author Holger
 */
class Empty extends TypeSafeMatcher<Collection<?>> {
    public Empty() {
    }

    @Override
    public boolean matchesSafely(Collection<?> item) {
        return item.isEmpty();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("collection is not empty");
    }

}
