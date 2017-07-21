package stories.UserStory;

import com.jayway.restassured.response.Response;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import stories.managers.SettingsManager;
import stories.model.shademodel.core.model.accountmodel.UserModel;
import stories.model.shademodel.core.model.accountmodel.UserModelResponse;
import stories.model.shademodel.core.model.jobmodel.*;
import stories.rest.APIFacade;
import stories.rest.responsecheck.ResponseCheckFactory;
import stories.test.BaseBackendTest;
import stories.util.SoftAssert;
import stories.util.ddto.DdtDataProvider;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Created by wizard on 20.07.2017.
 */
public class SaveJob extends BaseBackendTest {
    private static final String SAVE_JOB = "savedJob";
    private SettingsManager settingsManager;

    private APIFacade accountAPIFacade;
    public Integer userId2 = 0;
    public Integer userId3_1 = 0;
    public Integer userId3_2 = 0;
    public Integer jobId = 0;

 /*   @DataProvider(name = "savedJob")
    private Object[][] saveJob(ITestContext context)
            throws IOException {
        settingsManager = SettingsManager.getSettingsManager();
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        DdtDataProvider ddtDataProvider = new DdtDataProvider();
        return  ddtDataProvider.ddtProvider(
                context, SAVE_JOB,
                settingsManager.getDdtDataPath());
    }

*/
    @AfterClass(dependsOnMethods = "shutdownBaseBackendTest")
    public void deleteUser()
            throws IOException, SQLException {
        if (userId2 != 0) {
            accountAPIFacade.getAccountEndpoint().deleteUser(
                    userId2,
                    Collections.singletonList(ResponseCheckFactory.getStatusCodeCheck(200)),
                    "Deleting user with id: " + userId2 + ".");
        }
        if (userId3_1 != 0) {
            accountAPIFacade.getAccountEndpoint().deleteUser(
                    userId3_1,
                    Collections.singletonList(ResponseCheckFactory.getStatusCodeCheck(200)),
                    "Deleting user with id: " + userId3_1 + ".");
        }
        if (userId3_2 != 0) {
            accountAPIFacade.getAccountEndpoint().deleteUser(
                    userId3_2,
                    Collections.singletonList(ResponseCheckFactory.getStatusCodeCheck(200)),
                    "Deleting user with id: " + userId3_2 + ".");
        }
    }

    @BeforeTest()
    public void createEmployee() throws IOException, InterruptedException, SQLException {
        settingsManager = SettingsManager.getSettingsManager();
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        UserModelResponse response = null;

        String test_data = testDescription + "\n create user \n[ERROR] ";
        SoftAssert sa = new SoftAssert(test_data);
        //----Create Employer
        //Create the test user data without the DataProvider
        UserModel newUser2 = new UserModel();
        newUser2.setEmail("test_save_job_user2@distillery.com");
        newUser2.setFirstName("First");
        newUser2.setLastName("Petriv");
        newUser2.setPassword("qqqaaa77");
        newUser2.setUserType(2);
        //Sending the API request to the "/account/signup" endpoint and waiting 200 status code
     //   UserModelResponse response = null;
        response = (UserModelResponse) accountAPIFacade.getAccountEndpoint().createUser(
                newUser2,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
        System.out.println(" что-нибудь");
        userId2 = response.getAccess_token() != null ? response.getShadeUserModelResponse().getId() : 0;
    // */ userId2 = 932;
        //----Create Employee
        UserModel newUser3 = new UserModel();
        newUser3.setEmail("test_save_job_user3_1@distillery.com");
        newUser3.setFirstName("First");
        newUser3.setLastName("Petriv");
        newUser3.setPassword("qqqaaa77");
        newUser3.setUserType(3);
        //Sending the API request to the "/account/signup" endpoint and waiting 200 status code

        response = (UserModelResponse) accountAPIFacade.getAccountEndpoint().createUser(
                newUser3,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
        userId3_1 = response.getAccess_token() != null ? response.getShadeUserModelResponse().getId() : 0;

        newUser3.setEmail("test_save_job_user3_2@distillery.com");
        response = (UserModelResponse) accountAPIFacade.getAccountEndpoint().createUser(
                newUser3,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
        userId3_2 = response.getAccess_token() != null ? response.getShadeUserModelResponse().getId() : 0;

    }
    @Test /*(description = "save Job",
            dataProvider = "saveJob",
            priority = 1)*/
    public void saveJob() throws IOException, InterruptedException, SQLException {
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        settingsManager = SettingsManager.getSettingsManager();
        //----Create Job
        //Sending the API request to the "/account/signup" endpoint and waiting 200 status code
        final JobModel createJob = new JobModel();
        final Location location = new Location();
        location.setId(1);
//        location.setLatitude();
//        location.setLongitude();
        location.setAddress("street");

        createJob.setTitle("SuperJobForSave");
        createJob.setCategory(1);
        createJob.setLocation(location);
        createJob.setStartDate("2017-07-29T09:06:53.932Z");
        createJob.setStartTime("2017-07-29T09:06:53.932Z");
        createJob.setEndDate("2017-07-30T09:06:53.932Z");
        createJob.setEndTime("2017-07-30T09:06:53.932Z");
        createJob.setSalary(100);
        createJob.setSalaryType(1);
        createJob.setSummary("olololololo");
        createJob.setDescription("bububububu");
        createJob.setLocation(location);



        JobModelResponse responseJob = null;
        responseJob = (JobModelResponse) accountAPIFacade.getJobEndpoint().createJob(
                createJob, userId2,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
        jobId = responseJob.getId();
        String request = "{\"jobId\":" + responseJob.getId() + "}";
        //----save The Job
        Response response_save  = accountAPIFacade.getEmployeeEndpoint().saveJob(
                request, userId3_1,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);

        //get Job
        //job.getIsSave () != null
 /*       Response response_save2  = accountAPIFacade.getEmployeeEndpoint().saveJob(
                request, userId3_2,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
*/
        //----Get Applaed Job
        String request_saved = "?maxId=" + Integer.toString(jobId + 1) + "&sinceId=" + Integer.toString(jobId  - 1);
        List<JobFeedModelResponse> response_appled  = accountAPIFacade.getEmployeeEndpoint().savedJob(
                request_saved, userId3_1,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(response_appled.get(0).isSaved() == true);
        sa.assertAll();

        List<JobFeedModelResponse> response_appled2  = accountAPIFacade.getEmployeeEndpoint().savedJob(
                request_saved, userId3_2,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
        //sa.assertTrue(response_appled2.size() == 0);
        //get Job
        //job.getIsSave () != null
        sa.assertTrue(response_appled2 == null);
        sa.assertAll();

        request = "{\"jobId\":" + jobId + "}";
        Response response_delete  = accountAPIFacade.getEmployeeEndpoint().deleteSave(
                request, userId3_1,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);

        response_appled2  = accountAPIFacade.getEmployeeEndpoint().savedJob(
                request_saved, userId3_1,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);


        sa.assertTrue(response_appled2 == null);
        sa.assertAll();

    /*    List<JobErrorResponse> response_appled1  = accountAPIFacade.getEmployeeEndpoint().applaedJob(
                request_save, userId2,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
*/
      // SoftAssert sa = new SoftAssert();


     //   sa.assertNotNull(response_appled.get(0).getId());
     //   sa.assertNotNull(response_appled2.get(0).getId());
      // sa.assertAll();

    }




}
