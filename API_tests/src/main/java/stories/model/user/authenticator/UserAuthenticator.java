package stories.model.user.authenticator;


import stories.model.custom.BackendSettings;

import java.io.IOException;

/**
 * User class or it's subclasses does not perform authentication process itself.
 * Instead, they delegate this responsibility to the authenticators.
 */
public interface UserAuthenticator {
    AuthenticationData authenticate(String login, String password, BackendSettings backendSettings) throws IOException;
}
