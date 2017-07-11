package stories.util.polling;

/**
 * <p>
 * Base class for polling routines. Subclasses should implement one polling attempt.
 * Polling strategies itself are implemented in {@link PollingStrategies}.
 * <p>
 * All who will extend this class should override {@code makeAttempt()} method and
 * return true if one polling attempt was successful. False otherwise.
 * <p>
 * Client can save results of polling.
 * It is means that PollingAttempt save it's state and you should create new instance for each separate usage.
 *
 * @param <T> type of result of polling.
 */
public abstract class PollingAttempt<T> {
    private T result;

    public T getResult() {
        return result;
    }

    protected void setResult(T result) {
        this.result = result;
    }

    public abstract boolean makeAttempt();
}
