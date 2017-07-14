package stories.rest.endpoint;

import com.jayway.restassured.response.Response;
import stories.model.shademodel.core.model.accountmodel.UserModel;
import stories.model.shademodel.core.model.accountmodel.UserModelResponse;
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

public class AccountEndpoint extends AbstractEndpoint {
    public AccountEndpoint(Rest rest) {
        super(rest, "/account");
    }


    //API method for registering a new user
    public Object createUser(final UserModel request,
                                        final List<ResponseCheck> responseChecks,
                                        final String description) throws IOException
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Response response =  post(
                "/signup",
                headers,
                request,
               false,
                responseChecks,
                description);
        if (response.getStatusCode() == 200) {
            return responseMapper.readValue(response.asString(), UserModelResponse.class);
        } else {
            return responseMapper.readValue(response.asString(), UserErrorResponse.class);
        }
    }

    //API method for signing in
    public UserModelResponse loginUser(final UserModel request,
                                        final List<ResponseCheck> responseChecks,
                                        final String description) throws IOException
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Response response =  post(
                "/login",
                headers,
                request,
                false,
                responseChecks,
                description);
        return responseMapper.readValue(response.asString(), UserModelResponse.class);
    }

    /**
     * Delete specified user.
     *
     * @param userId      user you want to delete.
     * @param description description of your request.
     * @return response containing information about deleted user.
     * @throws IOException in case of network problems.
     */
    public UserModelResponse deleteUser(final int userId, final String description)
            throws IOException {
        Response response =
                deleteUser(
                        userId,
                        Collections.singletonList(
                                ResponseCheckFactory.getStatusCodeCheck(200)),
                        description);
        return response.as(UserModelResponse.class);
    }

    /**
     * Delete specified user.
     *
     * @param userId         user you want to delete.
     * @param responseChecks response checks.
     * @param description    description of your request.
     * @return raw response containing information about deleted user.
     * @throws IOException in case of network problems.
     */
    public Response deleteUser(final int userId, final List<ResponseCheck> responseChecks, final String description)
            throws IOException {
        return delete(
                "/delete?userId=" + Integer.toString(userId),
                null,
                false,
                responseChecks,
                description);
    }

    /**
     * Do login specified user.
     *
     * @param request                  containing user authentication data.
     * @param shouldPreserveNullFields should mapper preserve null fields in request payload.
     *                                 false if you want to remove fields with null values from request payload.
     * @param description              description of your request.
     * @return response containing logged user's info.
     * @throws IOException in case of network problems.
     */
    public UserBindingModel login(final LoginBindingModel request,
                                  final boolean shouldPreserveNullFields,
                                  final String description)
            throws IOException {
        Response response =
                login(
                        request,
                        shouldPreserveNullFields,
                        Collections.singletonList(
                                ResponseCheckFactory.getStatusCodeCheck(200)),
                        description);
        return responseMapper.readValue(response.asString(), UserBindingModel.class);
    }

    /**
     * Do login specified user.
     *
     * @param request                  containing user authentication data.
     * @param shouldPreserveNullFields should mapper preserve null fields in request payload.
     *                                 false if you want to remove fields with null values from request payload.
     * @param responseChecks           response checks.
     * @param description              description of your request.
     * @return raw response containing logged user's info.
     * @throws IOException in case of network problems.
     */
    public Response login(final LoginBindingModel request,
                          final boolean shouldPreserveNullFields,
                          final List<ResponseCheck> responseChecks,
                          final String description)
            throws IOException {
        return post(
                "/login",
                request,
                shouldPreserveNullFields,
                responseChecks,
                description);
    }

    /**
     * Do login specified user.
     *
     * @param request                  containing user authentication data.
     *                                 Building of request payload is up to caller.
     * @param shouldPreserveNullFields should mapper preserve null fields in request payload.
     *                                 false if you want to remove fields with null values from request payload.
     * @param responseChecks           response checks.
     * @param description              description of your request.
     * @return raw response containing logged user's info.
     * @throws IOException in case of network problems.
     */
    public Response login(final String request,
                          final boolean shouldPreserveNullFields,
                          final List<ResponseCheck> responseChecks,
                          final String description)
            throws IOException {
        return post(
                "/login",
                request,
                shouldPreserveNullFields,
                responseChecks,
                description);
    }

    /**
     * Get user info. User is determined by currently set authentication cookie.
     *
     * @param description description of your request.
     * @return response containing user's info.
     * @throws IOException in case of network problems.
     */
    public UserBindingModel getCurrentUser(final String description)
            throws IOException {
        Response response =
                getCurrentUser(
                        Collections.singletonList(
                                ResponseCheckFactory.getStatusCodeCheck(200)),
                        description);
        return responseMapper.readValue(response.asString(), UserBindingModel.class);
    }

    /**
     * Get user info. User is determined by currently set authentication cookie.
     *
     * @param responseChecks response checks.
     * @param description    description of your request.
     * @return raw response containing user's info.
     * @throws IOException in case of network problems.
     */
    public Response getCurrentUser(final List<ResponseCheck> responseChecks, final String description)
            throws IOException {
        return get(
                "/getUser",
                Collections.emptyMap(),
                responseChecks,
                description);
    }




}
