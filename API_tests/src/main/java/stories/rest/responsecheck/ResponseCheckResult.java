package stories.rest.responsecheck;

/**
 * Result of additional checks produces by {@link ResponseCheck}.
 */
public class ResponseCheckResult {
    private boolean checkResult;
    private String errorDescription;

    /**
     * Create a new ResponseCheckResult instance with check result set to false and error description set to empty string.
     */
    public ResponseCheckResult() {
        checkResult = false;
        errorDescription = "";
    }

    /**
     * Create a new pair instance.
     *
     * @param checkResult      the checkResult value, may be null.
     * @param errorDescription the errorDescription value, may be null.
     */
    public ResponseCheckResult(final boolean checkResult, final String errorDescription) {
        this.checkResult = checkResult;
        this.errorDescription = errorDescription;
    }

    /**
     * Set check result.
     *
     * @param checkResult set true if all checks were successful, false otherwise.
     */
    public void setCheckSuccess(final Boolean checkResult) {
        this.checkResult = checkResult;
    }

    public boolean isCheckSuccessful() {
        return this.checkResult;
    }

    /**
     * Set error description.
     *
     * @param errorDescription set error description if you have errors.
     */
    public void setErrorDescription(final String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorDescription() {
        return this.errorDescription;
    }
}
