package stories.UserStory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jayway.restassured.response.Response;
import org.testng.ITestContext;
import org.testng.annotations.*;
import stories.managers.SettingsManager;
import stories.model.custom.ErrorResponse;
import stories.model.shademodel.core.model.accountmodel.UserModel;
import stories.model.shademodel.core.model.accountmodel.UserModelResponse;
import stories.model.shademodel.core.model.chatmodel.ChatErrorResponse;
import stories.model.shademodel.core.model.chatmodel.ChatResponse;
import stories.model.shademodel.core.model.jobmodel.*;
import stories.rest.APIFacade;
import stories.rest.responsecheck.ResponseCheckFactory;
import stories.util.SoftAssert;
import stories.util.ddto.DdtDataProvider;
import stories.test.BaseBackendTest;
import stories.util.ddto.DdtoSet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by wizard on 17.07.2017.
 */
public class ApplayJob  extends BaseBackendTest{
    private static final String APPLAY_JOB = "applayJob";
    private SettingsManager settingsManager;

    private APIFacade accountAPIFacade;
    public Integer userId2 = 0;
    public Integer userId3_1 = 0;
    public Integer userId3_2 = 0;
    public Integer jobId = 0;



    /*@DataProvider(name = "applayJob")
    private Object[][] applayJob(ITestContext context)
            throws IOException {
        settingsManager = SettingsManager.getSettingsManager();
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        DdtDataProvider ddtDataProvider = new DdtDataProvider();
        return  ddtDataProvider.ddtProvider(
                context, APPLAY_JOB,
                settingsManager.getDdtDataPath());
    }*/
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

    @BeforeClass ()
    public void createUser() throws IOException, InterruptedException, SQLException {
        settingsManager = SettingsManager.getSettingsManager();
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        UserModelResponse response = null;

        String test_data = testDescription + "\n create user \n[ERROR] ";
        SoftAssert sa = new SoftAssert(test_data);
        //----Create Employer
        //Create the test user data without the DataProvider
        UserModel newUser2 = new UserModel();
        Date time = new Date();
        String rendom = String.valueOf(time.getTime());
        newUser2.setEmail("test_applay_job_user2" + rendom + "@distillery.com");
        newUser2.setFirstName("First");
        newUser2.setLastName("Petriv");
        newUser2.setPassword("qqqaaa77");
        newUser2.setUserType(2);
        //Sending the API request to the "/account/signup" endpoint and waiting 200 status code
       // UserModelResponse response = null;
        response = (UserModelResponse) accountAPIFacade.getAccountEndpoint().createUser(
                newUser2,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);

        userId2 = response.getAccess_token() != null ? response.getShadeUserModelResponse().getId() : 0;
    //  userId2 = 932;
        //----Create Employee
       UserModel newUser3 = new UserModel();
        newUser3.setEmail("test_applay_job_user3_1" + rendom + "@distillery.com");
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

        newUser3.setEmail("test_applay_job_user3_2" + rendom + "@distillery.com");
        response = (UserModelResponse) accountAPIFacade.getAccountEndpoint().createUser(
                newUser3,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
        userId3_2 = response.getAccess_token() != null ? response.getShadeUserModelResponse().getId() : 0;

    }



    @Test (/*description = "Applay Job",
            dataProvider = "applayJob",*/
             priority = 1)
        public void applayJob() throws IOException, InterruptedException, SQLException {
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        settingsManager = SettingsManager.getSettingsManager();

        //----Create Job
        //Sending the API request to the "/account/signup" endpoint and waiting 200 status code
        final JobModel createJob = new JobModel();
        final Location location = new Location();
        location.setId(1);
//        location.setLatitude();
//        location.setLongitude();
        location.setAddress("strit");

        createJob.setTitle("SuperJobForApplay");
        createJob.setCategory(1);
        createJob.setLocation(location);
        createJob.setStartDate("2017-12-29T09:06:53.932Z");
        createJob.setStartTime("2017-12-29T09:06:53.932Z");
        createJob.setEndDate("2017-12-30T09:06:53.932Z");
        createJob.setEndTime("2017-12-30T09:06:53.932Z");
        createJob.setSalary(100);
        createJob.setSalaryType("1");
        createJob.setSummary("olololololo");
        createJob.setDescription("bububububu");
        createJob.setUserId(userId2);

        JobModelResponse responseJob = null;
        responseJob = (JobModelResponse) accountAPIFacade.getJobEndpoint().createJob(
                createJob, userId2,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
        jobId = responseJob.getId();

        testCaseId = 0;
        String request = "{\"jobId\":" + responseJob.getId() + "}";
        //----Applay The Job
        Response response_applay = accountAPIFacade.getEmployeeEndpoint().applayJob(
                request, userId3_1,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);


      /*  JobErrorResponse response_appled1  = accountAPIFacade.getEmployeeEndpoint().applayJob(
                request, userId2,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);*/
        //----Get Applaed Job
        String request_appled = "?maxId=" + (jobId + 1) + "&sinceId=" + (jobId- 1);
        List<JobFeedModelResponse> response_appled = accountAPIFacade.getEmployeeEndpoint().applaedJob(
                request_appled, userId3_1,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);

        SoftAssert sa = new SoftAssert();
        accountAPIFacade.getJobEndpoint().checkJobNull(response_appled.get(0));
        System.out.print(response_appled.get(0).getId());
        sa.assertNotNull(response_appled.get(0).getId());

        }

        @Test (/*description = "Applay Job",
            dataProvider = "applayJob",*/
                priority = 2)
        public void applayJobBySecondUser() throws IOException, InterruptedException, SQLException {

        testCaseId = 0;
        String request = "{\"jobId\":" + jobId + "}";
        Response response_applay2 = accountAPIFacade.getEmployeeEndpoint().applayJob(
                    request, userId3_2,
                    Collections.singletonList(
                            ResponseCheckFactory.getStatusCodeCheck(200)),
                    testDescription);

        String request_appled = "?maxId=" + (jobId + 1) + "&sinceId=" + (jobId- 1);
        List<JobFeedModelResponse> response_appled2  = accountAPIFacade.getEmployeeEndpoint().applaedJob(
                request_appled, userId3_2,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);

   /*    List<JobErrorResponse> response_appled1  = accountAPIFacade.getEmployeeEndpoint().applaedJob(
                request_appled, userId2,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
*/
        SoftAssert sa = new SoftAssert();
        accountAPIFacade.getJobEndpoint().checkJobNull(response_appled2.get(0));
        System.out.print(response_appled2.get(0).getId());
        sa.assertNotNull(response_appled2.get(0).getId());
    }

    @Test (/*description = "Applay Job",
            dataProvider = "applayJob",*/
            priority = 3)
    public void declinedJob() throws IOException, InterruptedException, SQLException {
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        settingsManager = SettingsManager.getSettingsManager();
        testCaseId =0;
        String request = "{" +
                "  \"selfId\":" + userId3_1 +
                ",  \"jobId\":" + jobId +
                "}";
     /*   ChatResponse  response_decline = (ChatResponse) accountAPIFacade.getEmployeeEndpoint().declinejob(
                request,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
*/

        ErrorResponse response_decline2 = (ErrorResponse) accountAPIFacade.getEmployeeEndpoint().declinejob(
                request,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(400)),
                testDescription);

        SoftAssert sa = new SoftAssert();
        response_decline2.getErrorCode();
        sa.assertTrue(response_decline2.getErrorCode() == 307);
        sa.assertTrue(response_decline2.getErrorMessage().equals("The user has not been accepted to this job by the employer."));
        sa.assertAll();

    }



    //----Get Applaed Job


}
/*//////////////-----------
Response response_applay_error  = accountAPIFacade.getEmployeeEndpoint().applayJob(
        request, userId2,
        Collections.singletonList(
                ResponseCheckFactory.getStatusCodeCheck(200)),
        testDescription);

        */