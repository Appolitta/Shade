package stories.util.polling;

/**
 * Class containing different polling strategies.
 */
public final class PollingStrategies {
    private PollingStrategies() {
        throw new UnsupportedOperationException("You try to create instance of utility class.");
    }

    /**
     * Polling using supplied polling routine and times.
     *
     * @param pollingAttempt   object that will be used for polling attempts.
     *                         See {@link PollingAttempt} for more info.
     * @param pollingPeriod    time between pollingAttempt calls.
     *                         Actual time is pollingPeriod + time which need for 1 polling attempt.
     * @param totalPollingTime total time of polling.
     * @return true if polling is successful. False otherwise.
     * @throws InterruptedException in case of problem with waiting between polling attempts.
     */
    public static boolean periodicPolling(final PollingAttempt pollingAttempt,
                                          final long pollingPeriod,
                                          final long totalPollingTime)
            throws InterruptedException {
        final long end = System.currentTimeMillis() + totalPollingTime;
        while (true) {
            if (pollingAttempt.makeAttempt()) {
                return true;
            } else if (System.currentTimeMillis() > end) {
                return false;
            } else {
                Thread.sleep(pollingPeriod);
            }
        }
    }

    /**
     * Polling using supplied polling routine. Useful when one polling attempt can take long time.
     *
     * @param pollingAttempt   object that will be used for polling attempts.
     *                         See {@link PollingAttempt} for more info.
     * @param numberOfAttempts total number of polling attempts.
     * @param pollingPeriod    time between pollingAttempt calls.
     *                         Actual time is pollingPeriod + time which need for 1 polling attempt.
     * @return true if polling is successful. False otherwise.
     * @throws InterruptedException in case of problem with waiting between polling attempts.
     */
    public static boolean tryPoll(final PollingAttempt pollingAttempt,
                                  final int numberOfAttempts,
                                  final long pollingPeriod)
            throws InterruptedException {
        for (int i = 0; i < numberOfAttempts; ++i) {
            if (pollingAttempt.makeAttempt()) {
                return true;
            } else {
                Thread.sleep(pollingPeriod);
            }
        }
        return false;
    }
}
