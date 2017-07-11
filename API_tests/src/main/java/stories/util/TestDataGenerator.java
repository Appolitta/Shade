package stories.util;

import stories.model.shademodel.core.Roles;
import stories.db.DBFacade;
import stories.model.user.YourProjectUser;
import stories.rest.APIFacade;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.SQLException;

/**
 * Utils for generating different kind of test data.
 */
public final class TestDataGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();

    private TestDataGenerator() {
        throw new UnsupportedOperationException("You try to create instance of utility class.");
    }

    /**
     * Generate random alphanumeric string.
     *
     * @param prefix  string that will prepend random part.
     * @param postfix string that will be placed after random part.
     * @return randomly generated string with specified prefix and postfix.
     */
    public static String randomString(String prefix, String postfix) {
        return randomString(prefix, postfix, 64);
    }
    public static int randomIntRole(int employee,int employer) {
        return randomIntRole(employee,employer);
    }

    /**
     * Generate random alphanumeric string.
     *
     * @param prefix  string that will prepend random part.
     * @param postfix string that will be placed after random part.
     * @param numBits how "strong" should be random part of the string.
     * @return randomly generated string with specified prefix and postfix.
     */
    public static String randomString(String prefix, String postfix, int numBits) {
        return prefix + new BigInteger(numBits, secureRandom).toString(Character.MAX_RADIX) + postfix;
    }

    // Example of possible test generation method.
    /**
     * Create new user.
     *
     * @param userName  new user name.
     * @param password  password for the new user. Should be at least 6 symbols.
     * @param APIFacade client that will be used to create new user.
     * @return created user. User is not logged in.
     */
    public static YourProjectUser createYourProjectUserWithRole(final String userName,
                                                                final String password,
                                                                final Roles role,
                                                                final APIFacade APIFacade,
                                                                final DBFacade dbFacade)
            throws IOException, SQLException {
        return new YourProjectUser(userName, password, APIFacade.getBackendSettings());
    }
}
