package stories.rest.endpoint;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jayway.restassured.response.Response;
import stories.model.shademodel.core.model.accountmodel.UserModel;
import stories.model.shademodel.core.model.accountmodel.UserModelResponse;
import stories.model.shademodel.core.model.jobmodel.JobErrorResponse;
import stories.model.shademodel.core.model.jobmodel.JobFeedModelResponse;
import stories.model.shademodel.core.model.usermodel.UserAboutMeResponse;
import stories.model.shademodel.core.model.usermodel.UserErrorResponse;
import stories.model.shademodel.web.models.LoginBindingModel;
import stories.model.shademodel.web.models.UserBindingModel;
import stories.rest.Rest;
import stories.rest.responsecheck.ResponseCheck;
import stories.rest.responsecheck.ResponseCheckFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wizard on 14.07.2017.
 */
public class EmployeeEndpoint extends AbstractEndpoint{
    public EmployeeEndpoint(Rest rest) { super(rest, "employee/");}


    /**
     * Update user info
     *
     *
     *
     */
    public Object updateUser(final UserModel request,
                             Integer UserId,
                             final List<ResponseCheck> responseChecks,
                             final String description)
            throws  IOException{
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Response response =  put(
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
            throws  IOException{
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Response response =  put(
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

    public Response applayJob(String request,
                                Integer UserId,
                                final List<ResponseCheck> responseChecks,
                                final String description)
            throws  IOException{
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Response response =  post(
                Integer.toString(UserId) + "/applytojob",
                headers,
                request,
                false,
                responseChecks,
                description);
        if (response.getStatusCode() == 200) {
            return response;
        } else {
            return response;
        }
    }

    public List applaedJob(String request,
                              Integer UserId,
                              final List<ResponseCheck> responseChecks,
                              final String description)
            throws  IOException{
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Response response =  get(
                Integer.toString(UserId) + "/appliedjobs" + request,
                headers,
                responseChecks,
                description);

        if (response.getStatusCode() == 200) {
            List<JobFeedModelResponse> jb = responseMapper.readValue(response.asString(),  new TypeReference<List<JobFeedModelResponse>>(){});
            return jb;
            //responseMapper.readValue(response.asString(),  List.class);
        } else {
            List<JobErrorResponse> jb = responseMapper.readValue(response.asString(),  new TypeReference<List<JobErrorResponse>>(){});
            return jb;
        }


    }
}




