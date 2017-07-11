package stories.model.user.authenticator;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import stories.managers.GlobalObjectMappers;
import stories.model.custom.BackendSettings;
import stories.model.shademodel.web.models.LoginBindingModel;
import stories.model.shademodel.web.models.UserBindingModel;

import java.io.IOException;

public class YourUserAuthenticator implements UserAuthenticator {
    @Override
    public AuthenticationData authenticate(String login, String password, BackendSettings backendSettings)
            throws IOException {
        LoginBindingModel loginRequest = new LoginBindingModel();
        loginRequest.setUsername(login);
        loginRequest.setPassword(password);

        final String loginPath =
                "http://" + backendSettings.getServerUrl() + "/" + backendSettings.getBaseApiPath() + "/account/login";
        Response loginRawResponse =
                RestAssured
                        .given()
                        .contentType("application/json")
                        .body(GlobalObjectMappers.getObjectMapperWithoutNullFieldsPreserve().writeValueAsString(loginRequest))
                        .post(loginPath);

        assert loginRawResponse.getStatusCode() == 200 :
                "Login user " + login + " with password " + password + " using " + loginPath +
                        "\nExpected status code: 200; Actual status code: " + loginRawResponse.getStatusCode() +
                        "\nActual response: " + loginRawResponse.prettyPrint();

        UserBindingModel loginResponse =
                GlobalObjectMappers.getObjectMapperWithoutNullFieldsPreserve().readValue(loginRawResponse.asString(), UserBindingModel.class);

        AuthenticationData authData = new AuthenticationData();
        authData.setAuthenticationType(AuthenticationType.AUTHENTICATION_TYPE_YOUR_PROJECT);
        authData.setAuthCookie(loginRawResponse.getCookie(".AspNet.Cookies"));

        authData.setUserName(login);
        authData.setPassword(password);
        authData.setUserId(loginResponse.getId());

        return authData;
    }
}
