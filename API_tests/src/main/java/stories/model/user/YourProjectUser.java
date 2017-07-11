package stories.model.user;

import stories.model.custom.BackendSettings;
import stories.model.user.authenticator.AuthenticationData;
import stories.model.user.authenticator.UserAuthenticator;
import stories.model.user.authenticator.YourUserAuthenticator;

import java.io.IOException;
import java.sql.SQLException;

public class YourProjectUser extends User {
    private static final UserAuthenticator AUTHENTICATOR = new YourUserAuthenticator();

    public YourProjectUser(String login, String password, BackendSettings backendSettings)
            throws IOException, SQLException {
        this(login, password, false, false, backendSettings);
    }

    public YourProjectUser(String login, String password, boolean needToLogin, BackendSettings backendSettings)
            throws IOException, SQLException {
        this(login, password, needToLogin, false, backendSettings);
    }

    public YourProjectUser(String login,
                           String password,
                           boolean needToLogin,
                           boolean recreateUserBeforeLogin,
                           BackendSettings backendSettings,
                           Object... additionalArgs)
            throws IOException, SQLException {
        super(login, password, needToLogin, recreateUserBeforeLogin, backendSettings, additionalArgs);
    }

    @Override
    public void recreateUser(String login, String password) {
    }

    @Override
    public AuthenticationData authenticate(String login, String password)
            throws IOException {
        AuthenticationData authenticationData = AUTHENTICATOR.authenticate(login, password, getBackendSettings());
        setAuthenticationData(authenticationData);
        return getAuthenticationData();
    }
}
