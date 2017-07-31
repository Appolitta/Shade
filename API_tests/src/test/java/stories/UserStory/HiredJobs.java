package stories.UserStory;

import com.jayway.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.reporters.jq.INavigatorPanel;
import stories.managers.SettingsManager;
import stories.model.shademodel.core.model.accountmodel.UserModel;
import stories.model.shademodel.core.model.accountmodel.UserModelResponse;
import stories.model.shademodel.core.model.chatmodel.ChatErrorResponse;
import stories.model.shademodel.core.model.chatmodel.ChatResponse;
import stories.model.shademodel.core.model.jobmodel.*;
import stories.rest.APIFacade;
import stories.rest.responsecheck.ResponseCheckFactory;
import stories.test.BaseBackendTest;
import stories.util.SoftAssert;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by wizard on 24.07.2017.
 */

/**
 *
 UserId3_1 : applayJob,  accemptEmployer, accemptJob, hiredjobs , declinejob, cancelEmployee
 UserId3_2 : applayJob,  accemptEmployer,  declinejob, applayJob, acceptEmployee, cancelEmployee
 UserId3_3 :
 */
public class HiredJobs extends BaseBackendTest {

    private static final String APPLAY_JOB = "hiredJob";
    private SettingsManager settingsManager;

    private APIFacade accountAPIFacade;
    public Integer userId2 = 0;
    public Integer userId3_1 = 0;
    public Integer userId3_2 = 0;
    public Integer userId3_3 = 0;
    public String request = "";
    public int jobId = 0;

    public HiredJobs() throws IOException {
    }


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
/*    @AfterClass(dependsOnMethods = "shutdownBaseBackendTest")
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
        if (userId3_3 != 0) {
            accountAPIFacade.getAccountEndpoint().deleteUser(
                    userId3_3,
                    Collections.singletonList(ResponseCheckFactory.getStatusCodeCheck(200)),
                    "Deleting user with id: " + userId3_2 + ".");
        }

    }
*/
    @BeforeTest()
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
        newUser2.setEmail("test_hired_job_user2" + rendom + "@distillery.com");
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
        newUser3.setEmail("test_hired_job_user3_1" + rendom + "@distillery.com");
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

        newUser3.setEmail("test_hired_job_user3_2" + rendom +"@distillery.com");
        response = (UserModelResponse) accountAPIFacade.getAccountEndpoint().createUser(
                newUser3,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
        userId3_2 = response.getAccess_token() != null ? response.getShadeUserModelResponse().getId() : 0;

        newUser3.setEmail("test_hired_job_user3_3" + rendom + "@distillery.com");
        response = (UserModelResponse) accountAPIFacade.getAccountEndpoint().createUser(
                newUser3,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
        userId3_3 = response.getAccess_token() != null ? response.getShadeUserModelResponse().getId() : 0;
     /*       userId2 = 6;
        userId3_1 = 7;
*/
    }



    @Test (/*description = "Applay Job",
            dataProvider = "applayJob",*/
             priority = 1)
    public void hiredJob() throws IOException, InterruptedException, SQLException {
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

        request = "{\"jobId\":" + responseJob.getId() + "}";
        //----Applay The Job
        Response response_applay = accountAPIFacade.getEmployeeEndpoint().applayJob(
                request, userId3_1,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);

        //----Get Applaed Job
        String request_appled = "?maxId=" + Integer.toString(responseJob.getId() + 1) + "&sinceId=" + Integer.toString(responseJob.getId() - 1);
        List<JobFeedModelResponse> response_appled = accountAPIFacade.getEmployeeEndpoint().applaedJob(
                request_appled, userId3_1,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
        SoftAssert sa = new SoftAssert();
        accountAPIFacade.getJobEndpoint().checkJobNull(response_appled.get(0));
        System.out.print(response_appled.get(0).getId());
        sa.assertNotNull(response_appled.get(0).getId());


        //  SoftAssert sa = new SoftAssert();

     //   TimeUnit.SECONDS.sleep(600);
        //accempt employee wich applay the job
        request = "{\"selfId\":" + userId2 + ",\"employeeId\":" + userId3_1 + ",\"jobId\":" + responseJob.getId() + "}";

        ChatResponse response_accept = (ChatResponse) accountAPIFacade.getEmployerEndpoint().acceptEmployee(
                request,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);

        sa = new SoftAssert();
        sa.assertNotNull(response_accept.getChat());
        sa.assertNotNull(response_accept.getChat().getId());
 //       sa.assertNotNull(response_accept.getOpponentInfo().getId());
 //       sa.assertNotNull(response_accept.getOpponentInfo().getFirstName());
  //      sa.assertNotNull(response_accept.getJobInfo());
        sa.assertAll();

        request = "{\"selfId\":" + userId3_1 + ",\"jobId\":" + jobId + "}";

        Response response_acceptjob = (Response) accountAPIFacade.getEmployeeEndpoint().acceptjob(
                request, userId3_1,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);


        //hiredJob
        String feedJobRequest = "?maxId=" + Integer.toString(jobId + 1) + "&sinceId=" + Integer.toString(jobId - 1);
        List<JobFeedModelResponse> response = accountAPIFacade.getEmployeeEndpoint().hiredjobs(
                feedJobRequest, userId3_1,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
        sa = new SoftAssert();
        sa.assertTrue(response.get(0).getId() == jobId);
        sa.assertNotNull(response.get(0).getName());
        sa.assertTrue(response.get(0).getPoster().getId() == userId2 );
//проверки
        sa.assertAll();

        //The are no user's hierd job
        feedJobRequest = "?maxId=" + Integer.toString(jobId + 1) + "&sinceId=" + Integer.toString(jobId - 1);
        response = accountAPIFacade.getEmployeeEndpoint().hiredjobs(
                feedJobRequest, userId3_2,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
        sa = new SoftAssert();
        sa.assertTrue(response.size() == 0);
        sa.assertAll();




    }
    @Test(priority = 2)
    public void declinedJob() throws IOException, InterruptedException, SQLException {
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        settingsManager = SettingsManager.getSettingsManager();

        request = "{\"jobId\":" + jobId + "}";
        //----Applay The Job
        Response response_applay = accountAPIFacade.getEmployeeEndpoint().applayJob(
                request, userId3_2,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);


        request = "{\"selfId\":" + userId2 + ",\"employeeId\":" + userId3_2 + ",\"jobId\":" +jobId + "}";

        ChatResponse response_accept = (ChatResponse) accountAPIFacade.getEmployerEndpoint().acceptEmployee(
                request,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);

        SoftAssert sa = new SoftAssert();
        sa.assertNotNull(response_accept.getChat());
        sa.assertNotNull(response_accept.getChat().getId());
        //

        String request = "{" +
                "  \"selfId\":" + userId3_2 +
                ",  \"jobId\":" + jobId +
                "}";
        Response response_decline = (Response) accountAPIFacade.getEmployeeEndpoint().declinejob(
                request,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);

   /*     SoftAssert sa = new SoftAssert();
        sa.assertTrue(response_decline.getErrorCode() == 308);
        sa.assertTrue(response_decline.getErrorMessage().equals("The job has already been accepted by the employee."));
//проверки
        sa.assertAll();
*/
    }

    @Test (/*description = "Applay Job",
            dataProvider = "applayJob",*/
            priority = 3)
    public void declinedJobError() throws IOException, InterruptedException, SQLException {
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        settingsManager = SettingsManager.getSettingsManager();

        String request = "{\n" +
                "  \"selfId\":" + userId3_1 +
                ",  \"jobId\":" + jobId +
                "}";
        ChatErrorResponse response_decline = (ChatErrorResponse) accountAPIFacade.getEmployeeEndpoint().declinejob(
                request,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(400)),
                testDescription);

        SoftAssert sa = new SoftAssert();
        sa.assertTrue(response_decline.getErrorCode() == 308);
        sa.assertTrue(response_decline.getErrorMessage().equals("The job has already been accepted by the employee."));
//проверки
        sa.assertAll();

    }

    @Test (/*description = "Applay Job",
            dataProvider = "applayJob",*/
            priority = 4)
    public void cancelEmployee() throws IOException, InterruptedException, SQLException {
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        settingsManager = SettingsManager.getSettingsManager();

        request = "{\"jobId\":" + jobId + "}";
        //----Applay The Job
        Response response_applay = accountAPIFacade.getEmployeeEndpoint().applayJob(
                request, userId3_2,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);

        request = "{\"selfId\":" + userId2 + ",\"employeeId\":" + userId3_1 + ",\"jobId\":" + jobId + "}";

        ChatResponse response_accept = (ChatResponse) accountAPIFacade.getEmployerEndpoint().acceptEmployee(
                request,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);

        SoftAssert sa = new SoftAssert();
        sa.assertNotNull(response_accept.getChat());
        sa.assertNotNull(response_accept.getChat().getId());

        String request = "{" + "  \"selfId\":" + userId2 + "\"employeeId\":" + userId3_2 + ",\"jobId\":" + jobId + "}";

        ChatResponse response_decline = (ChatResponse) accountAPIFacade.getEmployerEndpoint().cancelEmployee(
                request,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);

    /*    SoftAssert sa = new SoftAssert();
        sa.assertTrue(response_decline.getErrorCode() == 308);
        sa.assertTrue(response_decline.getErrorMessage().equals("The job has already been accepted by the employee."));
//проверки*
        sa.assertAll();*/
    }


    @Test (/*description = "Applay Job",
            dataProvider = "applayJob",*/
            priority = 5)
    public void cancelEmployeeError() throws IOException, InterruptedException, SQLException {
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        settingsManager = SettingsManager.getSettingsManager();


        String request = "{" + "  \"selfId\":" + userId2 + ",\"employeeId\":" + userId3_1 + ",\"jobId\":" + jobId + "}";

        ChatErrorResponse response_decline = (ChatErrorResponse) accountAPIFacade.getEmployerEndpoint().cancelEmployee(
                request,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(400)),
                testDescription);

        SoftAssert sa = new SoftAssert();
        sa.assertTrue(response_decline.getErrorCode() == 308);
        sa.assertTrue(response_decline.getErrorMessage().equals("The job has already been accepted by the employee."));
//проверки
        sa.assertAll();
    }


}


