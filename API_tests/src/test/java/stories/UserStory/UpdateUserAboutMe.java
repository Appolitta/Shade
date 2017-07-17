package stories.UserStory;

import com.fasterxml.jackson.core.type.TypeReference;
import org.testng.ITestContext;
import org.testng.annotations.*;
import stories.db.DBFacade;
import stories.managers.SettingsManager;
import stories.model.shademodel.core.Roles;
import stories.model.shademodel.core.model.accountmodel.UserModel;
import stories.model.shademodel.core.model.accountmodel.UserModelResponse;
import stories.model.shademodel.core.model.jobmodel.Location;
import stories.model.shademodel.core.model.usermodel.UserAboutMeResponse;
import stories.model.shademodel.core.model.usermodel.UserErrorResponse;
import stories.rest.APIFacade;
import stories.rest.responsecheck.ResponseCheckFactory;
import stories.util.SoftAssert;
import stories.util.ddto.DdtDataProvider;
import stories.util.ddto.DdtoSet;
import stories.test.BaseBackendTest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import stories.test.BaseBackendTest;

/**
 * Created by wizard on 13.07.2017.
 */
public class UpdateUserAboutMe extends BaseBackendTest {

    private APIFacade accountAPIFacade;
    private DBFacade testDBFacade;
    private static final String ABOUT_ME_EMPLOYEE = "About_me_employee";
    private static final String ABOUT_ME_EMPLOYER = "About_me_employer";
    private static final String ABOUT_ME_NEG = "About_me_neg";


    private SettingsManager settingsManager;
    private int TEST_NUM = 1;
    public Integer userId = 0;


    //The DataProvider for the positive test
    @DataProvider(name = "About_me_employee")
    private Object[][] UpdateUserEmployee(ITestContext context)
            throws IOException {
        settingsManager = SettingsManager.getSettingsManager();
        testDBFacade = new DBFacade( settingsManager.getDefaultBackendSettings());
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        DdtDataProvider ddtDataProvider = new DdtDataProvider();
        return  ddtDataProvider.ddtProvider(
                context, ABOUT_ME_EMPLOYEE,
                settingsManager.getDdtDataPath());
    }

    @DataProvider(name = "About_me_employer")
    private Object[][] UpdateUserEmployer(ITestContext context)
            throws IOException {
        settingsManager = SettingsManager.getSettingsManager();
        testDBFacade = new DBFacade( settingsManager.getDefaultBackendSettings());
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        DdtDataProvider ddtDataProvider = new DdtDataProvider();
        return  ddtDataProvider.ddtProvider(
                context, ABOUT_ME_EMPLOYER,
                settingsManager.getDdtDataPath());
    }

    @DataProvider(name = "About_me_neg")
    private Object[][] UpdateUserNegative(ITestContext context)
            throws IOException {
        settingsManager = SettingsManager.getSettingsManager();
        testDBFacade = new DBFacade( settingsManager.getDefaultBackendSettings());
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        DdtDataProvider ddtDataProvider = new DdtDataProvider();
        return  ddtDataProvider.ddtProvider(
                context, ABOUT_ME_NEG,
                settingsManager.getDdtDataPath());
    }
/*   @BeforeClass
    public void createUser(/*final Map ddtSetMap)
 /*           throws IOException, SQLException {
        testCaseId = TEST_NUM + 1;
    //create user for change
        UserModel newUser = new UserModel();
        newUser.setEmail("test_udate_user2@distillery.com");
        newUser.setFirstName("First");
        newUser.setLastName("Petriv");
        newUser.setPassword("qqqaaa77");

    int role_id = 2;
        newUser.setUserType(role_id);
    //Sending the API request to the "/account/signup" endpoint and waiting 200 status code
    UserModelResponse response = null;
    response = (UserModelResponse) accountAPIFacade.getAccountEndpoint().createUser(
            newUser,
            Collections.singletonList(
                    ResponseCheckFactory.getStatusCodeCheck(200)),
    testDescription);
    userId  = response.getAccess_token() != null ? response.getShadeUserModelResponse().getId() : 0;
    }

*/
  //  @BeforeTest
  /*  @AfterTest(dependsOnMethods = "shutdownBaseBackendTest")
     public void deleteUser2()
             throws IOException, SQLException {
         if (userId != 0) {
             accountAPIFacade.getAccountEndpoint().deleteUser(
                     userId,
                     Collections.singletonList(ResponseCheckFactory.getStatusCodeCheck(200)),
                     "Deleting user with id: " + userId + ".");
         }
     }
    @AfterClass(dependsOnMethods = "shutdownBaseBackendTest")
/*    public void deleteUser()
            throws IOException, SQLException {
        if (userId != 0) {
            accountAPIFacade.getAccountEndpoint().deleteUser(
                    userId,
                    Collections.singletonList(ResponseCheckFactory.getStatusCodeCheck(200)),
                    "Deleting user with id: " + userId + ".");
        }
    }
 */
    @Test(description = "About me positive test",
            dataProvider = "About_me_employee",
            groups = {"accountAPIFacade, test-duration.short", "test-state.working"}, priority = 1)
    public void setAboutMeEmployeePositive(final Map ddtSetMap)
            throws IOException, SQLException {

             userId = 165;

            DdtoSet<UserAboutMeResponse> ddtoSet =
                    mapper.convertValue(ddtSetMap, new TypeReference<DdtoSet<UserAboutMeResponse>>() {
                    });
            //Sending the API request to the "/account/signup" endpoint and waiting 200 status code
            final UserAboutMeResponse updateUserRequest = ddtoSet.getDto();
            UserAboutMeResponse response2 = null;
            response2 = (UserAboutMeResponse) accountAPIFacade.getEmployeeEndpoint().updateUser(
                    updateUserRequest, userId,
                    Collections.singletonList(
                            ResponseCheckFactory.getStatusCodeCheck(ddtoSet.getStatusCode())),
                    testDescription);
            String test_data = ddtoSet.getDescription() + "\ncreate user test\nsetId:"
                    + testCaseId + "\n" + ddtoSet.getDescription() + "\n(caseId:" + testCaseId + ")\n[ERROR] ";


            //Make checks about the returned information
            SoftAssert sa = new SoftAssert(test_data);
            String first_name = updateUserRequest.getFirstName();
            String last_name = updateUserRequest.getLastName();
            String email = updateUserRequest.getEmail();
            String me = updateUserRequest.getAboutMe();
            String website = updateUserRequest.getWebsite();
            Location location = updateUserRequest.getLocation();

//       if (location != null) {
            //        Integer type = response.getUserType();
            //           sa.assertTrue(Roles.getById(user_id).getRoleDescription().equals(type));
            //    }
            //     sa.assertTrue(response.getAccess_token() != null, test_data);
            sa.assertTrue(first_name.equals(response2.getFirstName()));
            sa.assertTrue(last_name.equals(response2.getLastName()));
            sa.assertTrue(email.equals(response2.getEmail()));
            sa.assertTrue(me.equals(response2.getAboutMe()));
            sa.assertTrue(website.equals(response2.getWebsite()));
            sa.assertNotNull(location);
            userId = response2.getAccess_token() != null ? response2.getId() : 0;
            sa.assertAll();
        }

    @Test(description = "About me positive test",
            dataProvider = "About_me_employer",
            groups = {"accountAPIFacade, test-duration.short", "test-state.working"}, priority = 2)
    public void setAboutMeEmployerPositive(final Map ddtSetMap)
            throws IOException, SQLException {

        userId = 167;

        DdtoSet<UserAboutMeResponse> ddtoSet =
                mapper.convertValue(ddtSetMap, new TypeReference<DdtoSet<UserAboutMeResponse>>() {
                });
        //Sending the API request to the "/account/signup" endpoint and waiting 200 status code
        final UserAboutMeResponse updateUserRequest = ddtoSet.getDto();
        UserAboutMeResponse response2 = null;
        response2 = (UserAboutMeResponse) accountAPIFacade.getEmployerEndpoint().updateUser(
                updateUserRequest, userId,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(ddtoSet.getStatusCode())),
                testDescription);
        String test_data = ddtoSet.getDescription() + "\ncreate user test\nsetId:"
                + testCaseId + "\n" + ddtoSet.getDescription() + "\n(caseId:" + testCaseId + ")\n[ERROR] ";


        //Make checks about the returned information
        SoftAssert sa = new SoftAssert(test_data);
        String first_name = updateUserRequest.getFirstName();
        String last_name = updateUserRequest.getLastName();
        String email = updateUserRequest.getEmail();
        String me = updateUserRequest.getAboutMe();
        String website = updateUserRequest.getWebsite();
        Location location = updateUserRequest.getLocation();

//       if (location != null) {
        //        Integer type = response.getUserType();
        //           sa.assertTrue(Roles.getById(user_id).getRoleDescription().equals(type));
        //    }
        //     sa.assertTrue(response.getAccess_token() != null, test_data);
        sa.assertTrue(first_name.equals(response2.getFirstName()));
        sa.assertTrue(last_name.equals(response2.getLastName()));
        sa.assertTrue(email.equals(response2.getEmail()));
        sa.assertTrue(me.equals(response2.getAboutMe()));
        sa.assertTrue(website.equals(response2.getWebsite()));
        sa.assertNotNull(location);
        userId = response2.getAccess_token() != null ? response2.getId() : 0;
        sa.assertAll();
    }

    @Test(description = "About me negative test",
            dataProvider = "About_me_neg",
            groups = {"accountAPIFacade, test-duration.short", "test-state.working"}, priority = 3)
    public void setAboutMeEmployeeNegative(final Map ddtSetMap)
            throws IOException, SQLException {

        userId = 165;

        DdtoSet<UserModel> ddtoSet =
                mapper.convertValue(ddtSetMap, new TypeReference<DdtoSet<UserModel>>() {
                });
        //Sending the API request to the "/account/signup" endpoint and waiting 200 status code
        final UserModel updateUserRequest = ddtoSet.getDto();
        UserErrorResponse error_response_data = null;
        error_response_data = (UserErrorResponse) accountAPIFacade.getEmployeeEndpoint().updateUserNeg(
                updateUserRequest, userId,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(ddtoSet.getStatusCode())),
                testDescription);
        String test_data = ddtoSet.getDescription() + "\ncreate user test\nsetId:"
                + testCaseId + "\n" + ddtoSet.getDescription() + "\n(caseId:" + testCaseId + ")\n[ERROR] ";


       //Make checks about the returned information
        SoftAssert sa = new SoftAssert(test_data);

        int error_code = error_response_data.getErrorCode();
        String error_message = error_response_data.getErrorMessage();
        List<String> invalid_fields = error_response_data.getInvalidFields();
        sa.assertTrue(error_code == ddtoSet.getDto().getErrorCode());
        sa.assertTrue(error_message.equals(ddtoSet.getDto().getErrorMessage()));
        sa.assertTrue(invalid_fields.equals(ddtoSet.getDto().getInvalidFields()));
        sa.assertAll();

  }

    @Test(description = "About me negative test",
            dataProvider = "About_me_neg",
            groups = {"accountAPIFacade, test-duration.short", "test-state.working"}, priority = 4)
    public void setAboutMeEmployerNegative(final Map ddtSetMap)
            throws IOException, SQLException {

        userId = 167;

        DdtoSet<UserModel> ddtoSet =
                mapper.convertValue(ddtSetMap, new TypeReference<DdtoSet<UserModel>>() {
                });
        //Sending the API request to the "/account/signup" endpoint and waiting 200 status code
        final UserModel updateUserRequest = ddtoSet.getDto();
        UserErrorResponse error_response_data = null;
        error_response_data = (UserErrorResponse) accountAPIFacade.getEmployerEndpoint().updateUserNeg(
                updateUserRequest, userId,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(ddtoSet.getStatusCode())),
                testDescription);

        String test_data = ddtoSet.getDescription() + "\ncreate user test\nsetId:"
                + testCaseId + "\n" + ddtoSet.getDescription() + "\n(caseId:" + testCaseId + ")\n[ERROR] ";


        SoftAssert sa = new SoftAssert(test_data);
        int error_code = error_response_data.getErrorCode();
        String error_message = error_response_data.getErrorMessage();
        List <String> invalid_fields = error_response_data.getInvalidFields();
        sa.assertTrue(error_code == ddtoSet.getDto().getErrorCode());
        sa.assertTrue(error_message.equals(ddtoSet.getDto().getErrorMessage()));
        sa.assertTrue(invalid_fields.equals(ddtoSet.getDto().getInvalidFields()));
        sa.assertAll();
   }


}
