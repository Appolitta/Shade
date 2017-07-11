package stories.model.user;

import stories.model.user.authenticator.AuthenticationData;
import stories.model.custom.BackendSettings;

import java.io.IOException;
import java.sql.SQLException;


/**
 * Abstraction for user.
 */
public abstract class User {
    private String login;
    private String password;

    private BackendSettings backendSettings;

    private AuthenticationData authenticationData;

    /**
     * Create user.
     *
     * @param login                   user login.
     * @param password                user password.
     * @param needToLogin             user will be log in if set to true.
     * @param recreateUserBeforeLogin user will be deleted, then created, before login process started.
     * @param backendSettings         where our user located.
     * @param additionalArgs          additional args.
     * @throws IOException  in case of problem with login process.
     * @throws SQLException if recreation process use database and problem arise.
     */
    public User(String login,
                String password,
                boolean needToLogin,
                boolean recreateUserBeforeLogin,
                BackendSettings backendSettings,
                Object... additionalArgs)
            throws IOException, SQLException {

        this.login = login;
        this.password = password;
        setBackendSettings(backendSettings);

        if (recreateUserBeforeLogin) {
            recreateUser(getLogin(), getPassword());
        }

        if (needToLogin) {
            this.authenticationData = authenticate(getLogin(), getPassword());
        }
    }

    public AuthenticationData getAuthenticationData() {
        return authenticationData;
    }

    public void setAuthenticationData(AuthenticationData authenticationData) {
        this.authenticationData = authenticationData;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BackendSettings getBackendSettings() {
        return backendSettings;
    }

    public void setBackendSettings(BackendSettings backendSettings) {
        this.backendSettings = backendSettings;
    }

    /**
     * Subclasses should implement this method if they want to create "fresh" user before login.
     *
     * @param login    user login.
     * @param password user password.
     */
    public abstract void recreateUser(String login, String password);

    /**
     * Subclasses are responsible for login process implementation.
     *
     * @param login    user login.
     * @param password user password.
     * @return authentication data corresponding to the current user.
     * @throws IOException in case of problem with login process.
     */
    public abstract AuthenticationData authenticate(String login, String password)
            throws IOException;
}
