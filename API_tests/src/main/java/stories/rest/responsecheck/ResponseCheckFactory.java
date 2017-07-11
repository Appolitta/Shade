package stories.rest.responsecheck;

/**
 * Class for creating response checks. Better use it instead of creating checks manually.
 */
public class ResponseCheckFactory {
    // Most frequently used checks.
    private static final StatusCodeResponseCheck STATUS_CODE_RESPONSE_CHECK_EXPECT_200 =
            new StatusCodeResponseCheck(200);
    private static final StatusCodeResponseCheck STATUS_CODE_RESPONSE_CHECK_EXPECT_302 =
            new StatusCodeResponseCheck(302);
    private static final StatusCodeResponseCheck STATUS_CODE_RESPONSE_CHECK_EXPECT_400 =
            new StatusCodeResponseCheck(400);
    private static final StatusCodeResponseCheck STATUS_CODE_RESPONSE_CHECK_EXPECT_401 =
            new StatusCodeResponseCheck(401);
    private static final StatusCodeResponseCheck STATUS_CODE_RESPONSE_CHECK_EXPECT_403 =
            new StatusCodeResponseCheck(403);
    private static final StatusCodeResponseCheck STATUS_CODE_RESPONSE_CHECK_EXPECT_404 =
            new StatusCodeResponseCheck(404);

    public static ResponseCheck getStatusCodeCheck(final int expectedStatusCode) {
        switch (expectedStatusCode) {
            case 200:
                return STATUS_CODE_RESPONSE_CHECK_EXPECT_200;
            case 302:
                return STATUS_CODE_RESPONSE_CHECK_EXPECT_302;
            case 400:
                return STATUS_CODE_RESPONSE_CHECK_EXPECT_400;
            case 401:
                return STATUS_CODE_RESPONSE_CHECK_EXPECT_401;
            case 403:
                return STATUS_CODE_RESPONSE_CHECK_EXPECT_403;
            case 404:
                return STATUS_CODE_RESPONSE_CHECK_EXPECT_404;
            default:
                return new StatusCodeResponseCheck(expectedStatusCode);
        }
    }
}
