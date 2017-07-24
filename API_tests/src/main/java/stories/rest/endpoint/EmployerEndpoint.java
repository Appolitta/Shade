package stories.rest.endpoint;

import com.jayway.restassured.response.Response;
import stories.model.custom.ErrorResponse;
import stories.model.shademodel.core.model.accountmodel.UserModel;
import stories.model.shademodel.core.model.chatmodel.ChatErrorResponse;
import stories.model.shademodel.core.model.chatmodel.ChatResponse;
import stories.model.shademodel.core.model.usermodel.UserAboutMeResponse;
import stories.model.shademodel.core.model.usermodel.UserErrorResponse;
import stories.rest.Rest;
import stories.rest.responsecheck.ResponseCheck;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wizard on 14.07.2017.
 */
public class EmployerEndpoint extends AbstractEndpoint {
    public EmployerEndpoint(Rest rest) {
        super(rest, "employer/");
    }


    /**
     * Update user info
     */
    public Object updateUser(final UserModel request,
                             Integer UserId,
                             final List<ResponseCheck> responseChecks,
                             final String description)
            throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Response response = put(
                Integer.toString(UserId) + "/aboutme",
                headers,
                request,
                false,
                responseChecks,
                description);
        if (response.getStatusCode() == 200) {
            return responseMapper.readValue(response.asString(), UserAboutMeResponse.class);
        } else {
            return responseMapper.readValue(response.asString(), UserAboutMeResponse.class);
        }
    }

    public Object updateUserNeg(final UserModel request,
                                Integer UserId,
                                final List<ResponseCheck> responseChecks,
                                final String description)
            throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Response response = put(
                Integer.toString(UserId) + "/aboutme",
                headers,
                request,
                false,
                responseChecks,
                description);
        if (response.getStatusCode() == 200) {
            return responseMapper.readValue(response.asString(), UserErrorResponse.class);
        } else {
            return responseMapper.readValue(response.asString(), UserErrorResponse.class);
        }
    }

    public Object acceptEmployee(String request,

                                 final List<ResponseCheck> responseChecks,
                                 final String description)
            throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Response response = post(
                "acceptemployee",
                headers,
                request,
                false,
                responseChecks,
                description);
        if (response.getStatusCode() == 200) {
            return responseMapper.readValue(response.asString(), ChatResponse.class);
        } else {
            return responseMapper.readValue(response.asString(), ChatErrorResponse.class);
        }
    }

}


