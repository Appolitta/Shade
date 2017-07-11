package stories.rest.responsecheck;


import com.jayway.restassured.response.Response;

import java.util.function.Function;

/**
 * Interface for checks applied to the HTTP request response.
 */
public interface ResponseCheck extends Function<Response, ResponseCheckResult> {
}
