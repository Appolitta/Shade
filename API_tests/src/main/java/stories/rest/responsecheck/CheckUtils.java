package stories.rest.responsecheck;

import com.jayway.restassured.response.Response;
import stories.util.FormattingUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Mostly containing methods to improve checks readability.
 */
public final class CheckUtils {
    private CheckUtils() {
        throw new UnsupportedOperationException("You try to create instance of utility class.");
    }

    public static void assertResponse(final Response response,
                                      final List<ResponseCheck> responseChecks,
                                      final String requestPayload,
                                      final Map<String, String> requestHeaders,
                                      final Map<String, ?> requestParameters,
                                      final String restMethodFullPath,
                                      final String requestDescription)
            throws IOException {
        if (Objects.nonNull(responseChecks) && !responseChecks.isEmpty()) {
            ResponseCheckResult responseCheckResult;
            for (ResponseCheck responseCheck : responseChecks) {
                responseCheckResult = responseCheck.apply(response);
                if (!isChecksSuccessful(responseCheckResult)) {
                    String respBodyPretty = response.getContentType().contains("json")
                            ? FormattingUtils.jsonStringPretty(response.asString())
                            : response.prettyPrint();

                    String errorDescription =
                            requestDescription +
                                    FormattingUtils.requestDescription(
                                            restMethodFullPath,
                                            requestHeaders,
                                            requestParameters,
                                            requestPayload,
                                            4) +
                                    (respBodyPretty.equals("")
                                            ? "\nActual response body is empty"
                                            : "\nActual response:\n" + respBodyPretty) + "\n";

                    assert false : errorDescription + "\n" + responseCheckResult.getErrorDescription() + "\n";
                }
            }
        }
    }

    public static boolean isChecksSuccessful(final ResponseCheckResult responseCheckResult) {
        return responseCheckResult.isCheckSuccessful();
    }
}
