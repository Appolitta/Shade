package stories.UserStory;
import com.jayway.restassured.response.Response;
import org.testng.annotations.*;
import stories.db.DBFacade;
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
import stories.test.BaseBackendTest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by wizard on 21.07.2017.
 */
/**
UserId3_1 : applayJob,  accemptEmployer, accemptEmployer,
UserId3_2 : applayJob,                                   , canselEmployee,               accemptEmployer,
UserId3_3 :           , accemptEmployer,                                    ,applayJob,  accemptEmployer,
 */
public class AcceptEmployee extends BaseBackendTest {


    private static final String APPLAY_JOB = "applayJob";
    private SettingsManager settingsManager;
    private DBFacade testDBFacade;
    private APIFacade accountAPIFacade;
    public Integer userId2 = 0;
    public Integer userId3_1 = 0;
    public Integer userId3_2 = 0;
    public Integer userId3_3 = 0;
    public String request = "";
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
    @BeforeClass()
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

        newUser3.setEmail("test_applay_job_user3_3" + rendom + "@distillery.com");
        response = (UserModelResponse) accountAPIFacade.getAccountEndpoint().createUser(
                newUser3,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
        userId3_3 = response.getAccess_token() != null ? response.getShadeUserModelResponse().getId() : 0;

    }



    @Test (/*description = "Applay Job",
            dataProvider = "applayJob",*/
             priority = 1)
        public void acceptEmployeeJob() throws IOException, InterruptedException, SQLException {
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        settingsManager = SettingsManager.getSettingsManager();
        testDBFacade = new DBFacade( settingsManager.getDefaultBackendSettings());
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
        createJob.setStartDate("2017-12-01T09:06:53.932Z");
        createJob.setStartTime("2017-12-01T09:06:53.932Z");
        createJob.setEndDate("2017-12-02T09:06:53.932Z");
        createJob.setEndTime("2017-12-02T09:06:53.932Z");
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
        Response response_applay2 = accountAPIFacade.getEmployeeEndpoint().applayJob(
                request, userId3_2,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);


      /*  JobErrorResponse response_appled1  = accountAPIFacade.getEmployeeEndpoint().applayJob(
                request, userId2,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);*/
        //----Get Applaed Job
        String request_appled = "?maxId=" + (jobId + 1) + "&sinceId=" + (jobId - 1);
        List<JobFeedModelResponse> response_appled = accountAPIFacade.getEmployeeEndpoint().applaedJob(
                request_appled, userId3_1,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
        SoftAssert sa = new SoftAssert();
  //      accountAPIFacade.getJobEndpoint().checkJobNull(response_appled.get(0));
  //      sa.assertNotNull(response_appled.get(0).getId());


        List<JobFeedModelResponse> response_appled2 = accountAPIFacade.getEmployeeEndpoint().applaedJob(
                request_appled, userId3_2,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
  //      accountAPIFacade.getJobEndpoint().checkJobNull(response_appled2.get(0));
  ///      System.out.print(response_appled2.get(0).getId());
  //     sa.assertNotNull(response_appled2.get(0).getId());
  //     sa.assertAll();

         /*      List<JobErrorResponse> response_appled1 = accountAPIFacade.getEmployeeEndpoint().applaedJob(
                request_appled, userId2,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
*/
        //  SoftAssert sa = new SoftAssert();

        Integer appId = testDBFacade.getApplication(userId3_2, jobId);

        request = "{\"selfId\":" + userId2 + ",\"applicationId\":" + appId + "}" ;
         Response read_applay = accountAPIFacade.getEmployerEndpoint().readApplication(
                request,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
        sa.assertNotNull(read_applay);



 //       TimeUnit.SECONDS.sleep(60);//временный костыль
        //accempt employee wich applay the job
        request = "{\"selfId\":" + userId2 + ",\"employeeId\":" + userId3_1 + ",\"jobId\":" + jobId + "}";

        ChatResponse response_accept = (ChatResponse) accountAPIFacade.getEmployerEndpoint().acceptEmployee(
                request,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
        sa.assertNotNull(response_accept.getChat());
        sa.assertNotNull(response_accept.getChat().getId());
        //      sa.assertNotNull(response_accept.getOpponentInfo().getId());
        //      sa.assertNotNull(response_accept.getOpponentInfo().getFirstName());
        //     sa.assertNotNull(response_accept.getJobInfo());
        sa.assertAll();
    /*    Response response = accountAPIFacade.getEmployerEndpoint().acceptEmployee(
                request,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
*/
        //accempt employee wich don't applay the job
        request = "{\"selfId\":" + userId2 + ",\"employeeId\":" + userId3_3 + ",\"jobId\":" + responseJob.getId() + "}";
        ChatErrorResponse response_error = (ChatErrorResponse) accountAPIFacade.getEmployerEndpoint().acceptEmployee(
                request,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(400)),
                testDescription);


        sa = new SoftAssert();
        response_error.getErrorCode();
        sa.assertTrue(response_error.getErrorCode() == 402);
        sa.assertTrue(response_error.getErrorMessage().equals("Request from this employee for this job is not found."));
        sa.assertAll();


        //accempt employee twise
        request = "{\"selfId\":" + userId2 + ",\"employeeId\":" + userId3_1 + ",\"jobId\":" + jobId + "}";

        response_error = (ChatErrorResponse) accountAPIFacade.getEmployerEndpoint().acceptEmployee(
                request,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(400)),
                testDescription);

        sa = new SoftAssert();
        response_error.getErrorCode();
        sa.assertTrue(response_error.getErrorCode() == 210);
        sa.assertTrue(response_error.getErrorMessage().equals("The job offer status is not suitable for this action."));

    //    sa.assertTrue(response_error.getErrorCode() == 401);
    //    sa.assertTrue(response_error.getErrorMessage().equals("The employer has already accepted a request to this job."));


        sa.assertAll();



    }

        @Test (/*description = "Applay Job",
            dataProvider = "applayJob",*/
             priority = 2)
        public void cancelEmployee() throws IOException, InterruptedException, SQLException {
            testDBFacade = new DBFacade( settingsManager.getDefaultBackendSettings());
            request = "{\"selfId\":" + userId2 + ",\"employeeId\":" + userId3_1 + ",\"jobId\":" + jobId + "}";
            SoftAssert sa = new SoftAssert();
            final JobModel createJob = new JobModel();
            final Location location = new Location();
            location.setId(1);
//        location.setLatitude();
//        location.setLongitude();
            location.setAddress("strit");


            createJob.setTitle("SuperJobForApplay");
            createJob.setCategory(1);
            createJob.setLocation(location);
            createJob.setStartDate("2017-12-01T09:06:53.932Z");
            createJob.setStartTime("2017-12-01T09:06:53.932Z");
            createJob.setEndDate("2017-12-02T09:06:53.932Z");
            createJob.setEndTime("2017-12-02T09:06:53.932Z");
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
            int jobId = responseJob.getId();
    /*        ChatResponse response_accept = (ChatResponse) accountAPIFacade.getEmployerEndpoint().acceptEmployee(
                    request,
                    Collections.singletonList(
                            ResponseCheckFactory.getStatusCodeCheck(200)),
                    testDescription);

            SoftAssert sa = new SoftAssert();
            sa.assertNotNull(response_accept.getChat());
            sa.assertNotNull(response_accept.getChat().getId());
            //      sa.assertNotNull(response_accept.getOpponentInfo().getId());
            //      sa.assertNotNull(response_accept.getOpponentInfo().getFirstName());
            //     sa.assertNotNull(response_accept.getJobInfo());
            sa.assertAll();
*/          request  = "{\"jobId\":" + jobId + "}";
            Response response_applay2 = accountAPIFacade.getEmployeeEndpoint().applayJob(
                    request, userId3_2,
                    Collections.singletonList(
                            ResponseCheckFactory.getStatusCodeCheck(200)),
                    testDescription);

            request = "{\"selfId\":" + userId2 + ",\"employeeId\":" + userId3_2 + ",\"jobId\":" + jobId + "}";
            ChatResponse response_accept = (ChatResponse) accountAPIFacade.getEmployerEndpoint().acceptEmployee(
                    request,
                    Collections.singletonList(
                            ResponseCheckFactory.getStatusCodeCheck(200)),
                    testDescription);
            sa.assertNotNull(response_accept);
            sa.assertAll();

        String request = "{\"selfId\":" + userId2 + ",\"employeeId\":" + userId3_2 + ",\"jobId\":" + jobId + "}";
        Response response_cancel = (Response) accountAPIFacade.getEmployerEndpoint().cancelEmployee(
                request,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
        sa.assertNotNull(response_cancel);

     //   SoftAssert sa = new SoftAssert();


        //SHADE-440
        request = "{\"selfId\":" + userId2 + ",\"employeeId\":" + userId3_2 + ",\"jobId\":" + jobId + "}";
         Response response_accept2 = (Response) accountAPIFacade.getEmployerEndpoint().acceptEmployee2(
                    request,
                    Collections.singletonList(
                            ResponseCheckFactory.getStatusCodeCheck(200)),
                    testDescription);
        sa.assertNotNull(response_accept);
        sa.assertAll();

      /*  sa.assertTrue(response_error.getErrorCode() == 401);
        sa.assertTrue(response_error.getErrorMessage().equals("The employer has already accepted a request to this job."));
      */  sa.assertAll();


    }

    @Test(priority = 3)
    public void acceptTwoEmployee() throws IOException, InterruptedException, SQLException {

        String request = "{\"jobId\":" + jobId + "}";
        Response response_applay = accountAPIFacade.getEmployeeEndpoint().applayJob(
                request, userId3_3,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);


        request = "{\"selfId\":" + userId2 + ",\"employeeId\":" + userId3_3 + ",\"jobId\":" + jobId + "}";
        ChatResponse response_accept = (ChatResponse) accountAPIFacade.getEmployerEndpoint().acceptEmployee(
                request,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
        SoftAssert sa = new SoftAssert();
        sa.assertNotNull(response_accept.getChat());
        sa.assertNotNull(response_accept.getChat().getId());
        sa.assertAll();


    }
//-----negativ------------///
    /**проверка canceljoboffer при условии, что employee уже приналя job offer или отказался от него
*/
  /* @Test(priority = 4)
    public void declineJobOffer() throws IOException, InterruptedException, SQLException {

        String request = "{\"jobId\":" + jobId + "}";
        Response response_applay = accountAPIFacade.getEmployeeEndpoint().applayJob(
                request, userId3_3,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);

/*
       Response response_accept = (ChatResponse) accountAPIFacade.getEmployeeEndpoint().declinejob(
               request,
               Collections.singletonList(
                       ResponseCheckFactory.getStatusCodeCheck(200)),
               testDescription);

        request = "{\"selfId\":" + userId2 + ",\"employeeId\":" + userId3_3 + ",\"jobId\":" + jobId + "}";
        ChatResponse response_accept = (ChatResponse) accountAPIFacade.getEmployerEndpoint().acceptEmployee(
                request,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
        SoftAssert sa = new SoftAssert();
        sa.assertNotNull(response_accept.getChat());
        sa.assertNotNull(response_accept.getChat().getId());
        sa.assertAll();


    }
/*
    @Test(priority = 5)
    public void accemptJobOffer() throws IOException, InterruptedException, SQLException {

        String request = "{\"jobId\":" + jobId + "}";
        Response response_applay = accountAPIFacade.getEmployeeEndpoint().applayJob(
                request, userId3_3,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);


        request = "{\"selfId\":" + userId2 + ",\"employeeId\":" + userId3_3 + ",\"jobId\":" + jobId + "}";
        ChatResponse response_accept = (ChatResponse) accountAPIFacade.getEmployerEndpoint().acceptEmployee(
                request,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);
        SoftAssert sa = new SoftAssert();
        sa.assertNotNull(response_accept.getChat());
        sa.assertNotNull(response_accept.getChat().getId());
        sa.assertAll();


    }*/
}