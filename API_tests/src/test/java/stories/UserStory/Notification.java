package stories.UserStory;

import com.jayway.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import stories.managers.SettingsManager;
import stories.model.shademodel.core.model.accountmodel.UserModel;
import stories.model.shademodel.core.model.accountmodel.UserModelResponse;
import stories.model.shademodel.core.model.chatmodel.ChatResponse;
import stories.model.shademodel.core.model.jobmodel.JobFeedModelResponse;
import stories.model.shademodel.core.model.jobmodel.JobModel;
import stories.model.shademodel.core.model.jobmodel.JobModelResponse;
import stories.model.shademodel.core.model.jobmodel.Location;
import stories.rest.APIFacade;
import stories.rest.responsecheck.ResponseCheckFactory;
import stories.test.BaseBackendTest;
import stories.util.SoftAssert;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by wizard on 31.07.2017.
 */
public class Notification extends BaseBackendTest {


    private SettingsManager settingsManager;
    private APIFacade accountAPIFacade;

    public Integer userId2 = 0;
    public Integer userId3_1 = 0;
    public Integer jobId = 0;
    public Integer chatId = 0;

    public Notification() throws IOException {
    }


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
        newUser2.setEmail("test_notification_chat_user2" + rendom + "@distillery.com");
        newUser2.setFirstName("Petr");
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

        //----Create Employee
        UserModel newUser3 = new UserModel();
        newUser3.setEmail("test_notification_chat_user3_1" + rendom + "@distillery.com");
        newUser3.setFirstName("Petr");
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


    }

        @Test(/*description = "Applay Job",
            dataProvider = "applayJob",*/
                priority = 1)
        public void CreateChatJob () throws IOException, InterruptedException, SQLException {
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

            createJob.setTitle("SuperJobForNotification");
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

            String request = "{\"jobId\":" + jobId + "}";
            //----Applay The Job
            Response response_applay = accountAPIFacade.getEmployeeEndpoint().applayJob(
                    request, userId3_1,
                    Collections.singletonList(
                            ResponseCheckFactory.getStatusCodeCheck(200)),
                    testDescription);

            //accempt employee wich applay the job
            request = "{\"selfId\":" + userId2 + ",\"employeeId\":" + userId3_1 + ",\"jobId\":" + jobId + "}";

            ChatResponse response_accept = (ChatResponse) accountAPIFacade.getEmployerEndpoint().acceptEmployee(
                    request,
                    Collections.singletonList(
                            ResponseCheckFactory.getStatusCodeCheck(200)),
                    testDescription);

            SoftAssert sa = new SoftAssert();
            sa.assertNotNull(response_accept.getChat());
            sa.assertNotNull(response_accept.getChat().getId());
            chatId = response_accept.getChat().getId();
            sa.assertAll();

        }
    @Test (priority = 2)
    public void unreadChat () throws IOException, InterruptedException, SQLException    {
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        settingsManager = SettingsManager.getSettingsManager();


        Response response = (Response) accountAPIFacade.getNotification().unreadForChat(
                userId2,
                chatId,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);

        Response response2 = (Response) accountAPIFacade.getNotification().unreadForChat(
                userId2,
                chatId,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);

        SoftAssert sa = new SoftAssert();
        sa.assertNotNull(response);
        sa.assertNotNull(response2);
        sa.assertAll();
    }

 /*   @Test (priority = 3)
    public void markRead () throws IOException, InterruptedException, SQLException    {
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        settingsManager = SettingsManager.getSettingsManager();
        int notId = 0;

        getNotification(userId2, ChatId);
        String request = "{\"selfId\":" + userId2 +",\"notificationId\":" + notId +"}";
        Response response = (Response) accountAPIFacade.getNotification().markRead(
                userId2,
                request,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);

        SoftAssert sa = new SoftAssert();
        sa.assertNotNull(response);
        sa.assertAll();
    }
*/
    @Test (priority = 5)
    public void deviseToken () throws IOException, InterruptedException, SQLException {
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        settingsManager = SettingsManager.getSettingsManager();
        int notId = 0;

        String request = "{\"selfId\":" + userId2 + ",\"token\":\"Good\"}";
        Response response = accountAPIFacade.getAccountEndpoint().deviseToken(
                request,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);

        SoftAssert sa = new SoftAssert();
        sa.assertNotNull(response);
        sa.assertAll();


        request = "{\"selfId\":" + userId3_1 + ",\"token\":\"Good\"}";
        response =  accountAPIFacade.getAccountEndpoint().deviseToken(
               request,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(200)),
                testDescription);

        sa = new SoftAssert();
        sa.assertNotNull(response);
        sa.assertAll();

    }


}