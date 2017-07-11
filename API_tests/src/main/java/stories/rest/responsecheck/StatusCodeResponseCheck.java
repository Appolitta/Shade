package stories.rest.responsecheck;

import com.jayway.restassured.response.Response;

public class StatusCodeResponseCheck implements ResponseCheck {
    private final int expectedStatusCode;

    public StatusCodeResponseCheck(final int expectedStatusCode) {
        assert (expectedStatusCode >= 100 && expectedStatusCode <= 511) :
                "Incorrect expected status code " + expectedStatusCode +
                        ". Status code should be between 100 and 511 inclusive.";

        this.expectedStatusCode = expectedStatusCode;
    }

    @Override
    public ResponseCheckResult apply(Response response) {
        ResponseCheckResult result = new ResponseCheckResult(true, "");

        if (response.getStatusCode() != expectedStatusCode) {
            result.setCheckSuccess(false);
            result.setErrorDescription(
                    "Expected status code: " + expectedStatusCode +
                            "; Actual status code: " + response.getStatusCode());
        }

        return result;
    }
}
