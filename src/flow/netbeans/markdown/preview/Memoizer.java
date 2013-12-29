
package flow.netbeans.markdown.preview;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author Holger Stenger
 */
public class Memoizer<T> {
    private final Callable<T> callable;

    private boolean initialized;

    private T value;

    private Exception exception;

    public Memoizer(Callable<T> callable) {
        this.callable = callable;
        this.initialized = false;
        this.value = null;
        this.exception = null;
    }

    public synchronized T get() throws ExecutionException {
        if (!initialized) {
            try {
                value = callable.call();
            }
            catch (Exception ex) {
                exception = ex;
            }
            initialized = true;
        }
        if (exception != null) {
            throw new ExecutionException(exception);
        }
        else {
            return value;
        }
    }

}
