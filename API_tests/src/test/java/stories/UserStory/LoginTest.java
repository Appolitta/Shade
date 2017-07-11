package stories.UserStory;

import com.fasterxml.jackson.core.type.TypeReference;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import stories.db.DBFacade;
import stories.managers.SettingsManager;
import stories.model.shademodel.core.Roles;
import stories.model.shademodel.core.model.accountmodel.UserModel;
import stories.model.shademodel.core.model.accountmodel.UserModelResponse;
import stories.model.user.YourProjectUser;
import stories.rest.APIFacade;
import stories.rest.responsecheck.ResponseCheckFactory;
import stories.test.BaseBackendTest;
import stories.util.SoftAssert;
import stories.util.TestDataGenerator;
import stories.util.ddto.DdtDataProvider;
import stories.util.ddto.DdtoSet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

/**
 * Created by weezlabs on 4/19/17.
 */


@Test(groups = {"user.login"})
public class LoginTest extends BaseBackendTest {
    private static final String LOGIN_USER = "signinUser";
    private int TEST_NUM = 1;
    private Integer userId = 0;

    private APIFacade accountAPIFacade;

    private SettingsManager settingsManager;

    @BeforeClass

    public void setUp()
            throws IOException, SQLException {
        settingsManager = SettingsManager.getSettingsManager();

        //Create the test user data without the DataProvider
        UserModel newUser = new UserModel();
        int random_role = TestDataGenerator.randomIntRole(2,3);
        newUser.setEmail("test_user_login@gmail.com");
        newUser.setFirstName("random_name");
        newUser.setLastName("random_last_name");
        newUser.setPassword("qqqaaa7");
        newUser.setUserType(random_role);

        //Sending the API request to the "/account/signup" endpoint and waiting 200 status code
        UserModelResponse response = null;
        response = (UserModelResponse) accountAPIFacade.getAccountEndpoint().createUser(
                newUser,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
        userId = response.getAccess_token() != null ? response.getShadeUserModelResponse().getId() : 0;
    }

    //The DataProvider for the positive test
    @DataProvider(name = "LoginUser")
    private Object[][] createUser(ITestContext context)
            throws IOException {
        settingsManager = SettingsManager.getSettingsManager();
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        DdtDataProvider ddtDataProvider = new DdtDataProvider();
        return  ddtDataProvider.ddtProvider(
                context, LOGIN_USER,
                settingsManager.getDdtDataPath());
    }

    //Delete user after test was executed
    @AfterClass(dependsOnMethods = "shutdownBaseBackendTest")
    public void deleteUser()
            throws IOException, SQLException {
        if (userId != 0) {
            accountAPIFacade.getAccountEndpoint().deleteUser(
                    userId,
                    Collections.singletonList(ResponseCheckFactory.getStatusCodeCheck(200)),
                    "Deleting user with id: " + userId + ".");
        }
    }

    @Test(description = "Login to the user account test",
            dataProvider = "LoginUser",
            groups = {"accountAPIFacade, test-duration.short", "test-state.working"}, priority = 1)
    public void createUserPositive(final Map ddtSetMap)
            throws IOException, SQLException {
        testCaseId = TEST_NUM + 1;

        DdtoSet<UserModel> ddtoSet =
                mapper.convertValue(ddtSetMap, new TypeReference<DdtoSet<UserModel>>() {
                });
        //Sending the API request to the "/account/login" endpoint and waiting 200 status code
        final UserModel createUserRequest = ddtoSet.getDto();

        UserModelResponse response = null;
        response = accountAPIFacade.getAccountEndpoint().loginUser(
                createUserRequest,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(ddtoSet.getStatusCode())),
                testDescription);
        String test_data = ddtoSet.getDescription() + "\ncreate user test\nsetId:"
                + testCaseId + "\n" + ddtoSet.getDescription() + "\n(caseId:" + testCaseId + ")\n[ERROR] ";


        //Make checks about the returned information
        SoftAssert sa = new SoftAssert(test_data);
        String first_name = createUserRequest.getFirstName();
        String last_name = createUserRequest.getLastName();
        String email = createUserRequest.getEmail();
        int user_type = createUserRequest.getUserType();

        if (user_type != 0) {
            String type = response.getShadeUserModelResponse().getType();
            sa.assertTrue(Roles.getById(user_type).getRoleDescription().equals(type));
        }
        sa.assertTrue(response.getAccess_token() != null, test_data);
        sa.assertTrue(first_name.equals(response.getFirstName()));
        sa.assertTrue(last_name.equals(response.getLastName()));
        sa.assertTrue(email.equals(response.getEmail()));
        userId = response.getAccess_token() != null ? response.getShadeUserModelResponse().getId() : 0;
        sa.assertAll();
    }


}
