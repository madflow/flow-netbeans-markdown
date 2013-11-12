
package flow.netbeans.markdown.matchers;

import java.util.Collection;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 *
 * @author Holger
 */
class Empty extends BaseMatcher<Collection<?>> {

    public Empty() {
    }

    @Override
    public boolean matches(Object item) {
        if (item instanceof Collection<?>) {
            final Collection<?> collection = (Collection<?>) item;
            return collection.isEmpty();
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("collection is not empty");
    }

}
