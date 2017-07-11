package stories.UserStory;

import com.fasterxml.jackson.core.type.TypeReference;
import org.testng.annotations.AfterClass;
import stories.managers.SettingsManager;
import stories.model.shademodel.core.Roles;
import stories.model.shademodel.core.model.accountmodel.UserModel;
import stories.model.shademodel.core.model.accountmodel.UserModelResponse;
import stories.model.shademodel.core.model.usermodel.UserErrorResponse;
import stories.model.shademodel.db.JobsDBModel;
import stories.model.job.Job;
import stories.rest.APIFacade;
import stories.rest.responsecheck.ResponseCheckFactory;
import stories.test.BaseBackendTest;
import stories.util.SoftAssert;
import stories.util.TestDataGenerator;
import stories.util.ddto.DdtDataProvider;
import stories.util.ddto.DdtoSet;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by weezlabs on 4/3/17.
 */

@Test(groups = {"user.registration"})
public class SignUpTest extends BaseBackendTest {

    private static final String CREATE_USER = "createUser";
    private static final String CREATE_USER_NEG = "createUserNegative";
    private static final String SIGNIN_USER = "signinUser";
    private SettingsManager settingsManager;
    private int TEST_NUM = 1;
    private Integer userId = 0;

    private APIFacade accountAPIFacade;

    @BeforeClass

    //The DataProvider for the positive test
    @DataProvider(name = "CreateUser")
    private Object[][] createUser(ITestContext context)
            throws IOException {
        settingsManager = SettingsManager.getSettingsManager();
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        DdtDataProvider ddtDataProvider = new DdtDataProvider();
        return  ddtDataProvider.ddtProvider(
                    context, CREATE_USER,
                    settingsManager.getDdtDataPath());
    }

    //The DataProvider for the negative test
    @DataProvider(name = "CreateUserNegative")
    private Object[][] createUserNegative(ITestContext context)
            throws IOException {
        settingsManager = SettingsManager.getSettingsManager();
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        DdtDataProvider ddtDataProvider1 = new DdtDataProvider();
        return  ddtDataProvider1.ddtProvider(
                    context, CREATE_USER_NEG,
                    settingsManager.getDdtDataPath());
    }

    //The DataProvider for the negative test
    @DataProvider(name = "signinUser")
    private Object[][] signinUser(ITestContext context)
            throws IOException {
        settingsManager = SettingsManager.getSettingsManager();
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        DdtDataProvider ddtDataProvider1 = new DdtDataProvider();
        return  ddtDataProvider1.ddtProvider(
                context, SIGNIN_USER,
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

    @Test(description = "Create the user positive test",
            dataProvider = "CreateUser",
            groups = {"accountAPIFacade, test-duration.short", "test-state.working"}, priority = 1)
    public void createUserPositive(final Map ddtSetMap)
            throws IOException, SQLException {
        testCaseId = TEST_NUM + 1;

            DdtoSet<UserModel> ddtoSet =
                    mapper.convertValue(ddtSetMap, new TypeReference<DdtoSet<UserModel>>() {
                    });
            //Sending the API request to the "/account/signup" endpoint and waiting 200 status code
            final UserModel createUserRequest = ddtoSet.getDto();
        UserModelResponse response = null;
                     response = (UserModelResponse) accountAPIFacade.getAccountEndpoint().createUser(
                             createUserRequest,
                             Collections.singletonList(
                                     ResponseCheckFactory.getStatusCodeCheck(ddtoSet.getStatusCode())),
                             testDescription);
        String test_data =  ddtoSet.getDescription() + "\ncreate user test\nsetId:"
                + testCaseId + "\n" + ddtoSet.getDescription() + "\n(caseId:" + testCaseId + ")\n[ERROR] ";

 /*       //Make checks about the returned information
        SoftAssert sa = new SoftAssert(test_data);
        String first_name = response.getShadeUserModelResponse().getFirstName();
        String last_name = response.getShadeUserModelResponse().getLastName();
        String email = response.getShadeUserModelResponse().getEmail();
        String pass = response.getShadeUserModelResponse().getPassword();
        String user_type = response.getShadeUserModelResponse().getType();
        int idUser = response.getShadeUserModelResponse().getId();
        System.out.printf("Создан пользоватьель с ID" + idUser);
        System.out.printf("login" + email);
        System.out.printf("password" + pass);
      /*  if (user_type != 0) {
            String type = response.getShadeUserModelResponse().getType();
         sa.assertTrue(Roles.getById(user_type).getRoleDescription().equals(type));
        }
*/
   //     SoftAssert sa = new SoftAssert(test_data);
 /*       sa.assertTrue(response.getAccess_token() != null, test_data);
        sa.assertTrue(first_name.equals(response.getFirstName()));
        sa.assertTrue(last_name.equals(response.getLastName()));
        sa.assertTrue(email.equals(response.getEmail()));
        userId = response.getAccess_token() != null ? response.getShadeUserModelResponse().getId() : 0;
        sa.assertAll();
  */
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
        sa.assertTrue(first_name.equals(response.getShadeUserModelResponse().getFirstName()));
        sa.assertTrue(last_name.equals(response.getShadeUserModelResponse().getLastName()));
        sa.assertTrue(email.equals(response.getShadeUserModelResponse().getEmail()));
        userId = response.getAccess_token() != null ? response.getShadeUserModelResponse().getId() : 0;
        sa.assertAll();
    }

//}

    @Test(description = "Create the user negative test",
            dataProvider = "CreateUserNegative",
            groups = {"accountAPIFacade, test-duration.short", "test-state.working"}, priority = 2)
    public void createUserNegative(final Map ddtSetMap)
            throws IOException, SQLException {
        testCaseId = TEST_NUM + 1;

        DdtoSet<UserModel> ddtoSet =
                mapper.convertValue(ddtSetMap, new TypeReference<DdtoSet<UserModel>>() {
                });

        //Sending the API request to the "/account/signup" endpoint and waiting 400 status code
        final UserModel createUserRequest = ddtoSet.getDto();
        UserErrorResponse error_response_data = null;
        error_response_data = (UserErrorResponse) accountAPIFacade.getAccountEndpoint().createUser(
                createUserRequest,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(ddtoSet.getStatusCode())),
                testDescription);
        String test_data =  ddtoSet.getDescription() + " with the user type: " + ddtoSet.getDto().getUserType() + "\nCreate the user negative test\nsetId:"
                + testCaseId + "\n" + ddtoSet.getDescription() + "\n(caseId:" + testCaseId + ")\n[ERROR] ";

        //Make checks about the returned error code and error description
        SoftAssert sa = new SoftAssert(test_data);
        int error_code = error_response_data.getErrorCode();
        String error_message = error_response_data.getErrorMessage();
        List <String> invalid_fields = error_response_data.getInvalidFields();
        sa.assertTrue(error_code == ddtoSet.getDto().getErrorCode());
        sa.assertTrue(error_message.equals(ddtoSet.getDto().getErrorMessage()));
        sa.assertTrue(invalid_fields.equals(ddtoSet.getDto().getInvalidFields()));
        sa.assertAll();
 }

    @Test(description = "The user makes the attempt to register the second time with the same email", priority = 3 )
    public void userRegistrationTwice2() throws IOException, InterruptedException, SQLException {
        String test_data = testDescription + "\nuser registration  twice test\n[ERROR] ";
        SoftAssert sa = new SoftAssert(test_data);

        //Create the test user data without the DataProvider
        UserModel newUser = new UserModel();
        newUser.setEmail("test_twise_user2@distillery.com");
        newUser.setFirstName("First");
        newUser.setLastName("Petriv");
        newUser.setPassword("qqqaaa77");

        int role_id = 2;
    //    role_id = Roles.ROLES_EMPLOYER.getRoleId();
        newUser.setUserType(role_id);
        //Sending the API request to the "/account/signup" endpoint and waiting 200 status code
        UserModelResponse response = null;
        response = (UserModelResponse) accountAPIFacade.getAccountEndpoint().createUser(
                        newUser,
                        Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
        userId = response.getAccess_token() != null ? response.getShadeUserModelResponse().getId() : 0;

        //Sending the API request in the second time to the "/account/signup" endpoint and waiting 400 status code
        UserModel newUser2 = new UserModel();
        newUser2.setEmail("test_twise_user2@distillery.com");
        newUser2.setFirstName("First");
        newUser2.setLastName("Petriv");
        newUser2.setPassword("qqqaaa77");
        newUser2.setUserType(role_id);

        UserErrorResponse error_response = null;
        error_response = (UserErrorResponse)accountAPIFacade.getAccountEndpoint().createUser(
                newUser2,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(400)),
                testDescription);

        //Make checks about the returned error code and error description

        int error_code = error_response.getErrorCode();
        String error_message = error_response.getErrorMessage();
        sa.assertTrue(error_code == 105);
        sa.assertTrue(error_message.equals("The user with the same email already exists."));
        sa.assertAll();
    }

    @Test(description = "The user makes the attempt to register the second time with the same email", priority = 3 )
    public void userRegistrationTwice3() throws IOException, InterruptedException, SQLException {
        String test_data = testDescription + "\nuser registration  twice test\n[ERROR] ";
        SoftAssert sa = new SoftAssert(test_data);

        //Create the test user data without the DataProvider
        UserModel newUser = new UserModel();
        newUser.setEmail("test_twise_user3@distillery.com");
        newUser.setFirstName("First");
        newUser.setLastName("Petriv");
        newUser.setPassword("qqqaaa77");

        int role_id = 3;
        //    role_id = Roles.ROLES_EMPLOYER.getRoleId();
        newUser.setUserType(role_id);
        //Sending the API request to the "/account/signup" endpoint and waiting 200 status code
        UserModelResponse response = null;
        response = (UserModelResponse) accountAPIFacade.getAccountEndpoint().createUser(
                newUser,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
        userId = response.getAccess_token() != null ? response.getShadeUserModelResponse().getId() : 0;

        //Sending the API request in the second time to the "/account/signup" endpoint and waiting 400 status code
        UserModel newUser2 = new UserModel();
        newUser2.setEmail("test_twise_user3@distillery.com");
        newUser2.setFirstName("First");
        newUser2.setLastName("Petriv");
        newUser2.setPassword("qqqaaa77");
        newUser2.setUserType(role_id);

        UserErrorResponse error_response = null;
        error_response = (UserErrorResponse) accountAPIFacade.getAccountEndpoint().createUser(
                newUser2,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(400)),
                testDescription);

        //Make checks about the returned error code and error description

        int error_code = error_response.getErrorCode();

        String error_message = error_response.getErrorMessage();
        sa.assertTrue(error_code == 105);
        sa.assertTrue(error_message.equals("The user with the same email already exists."));
        sa.assertAll();


    }

}
