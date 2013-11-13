package flow.netbeans.markdown.matchers;

import java.util.Iterator;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

/**
 *
 * @author Holger
 */
public class EveryItem<T> extends TypeSafeMatcher<Iterable<? extends T>> {
    private final Matcher<T> itemMatcher;

    public EveryItem(Matcher<T> itemMatcher) {
        this.itemMatcher = itemMatcher;
    }

    @Override
    public boolean matchesSafely(Iterable<? extends T> item) {
        boolean result = true;
        Iterator<? extends T> iterator = item.iterator();
        while (result && iterator.hasNext()) {
            final T element = iterator.next();
            if (!itemMatcher.matches(element)) {
                result = false;
            }
        }
        return result;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("contains an element with ");
        description.appendDescriptionOf(itemMatcher);
    }

}
