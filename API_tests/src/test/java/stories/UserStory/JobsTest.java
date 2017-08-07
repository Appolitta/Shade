package stories.UserStory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jayway.restassured.response.Response;
import org.testng.annotations.*;
import stories.managers.SettingsManager;
import stories.model.custom.ErrorResponse;
import stories.model.shademodel.core.model.accountmodel.UserModel;
import stories.model.shademodel.core.model.accountmodel.UserModelResponse;
import stories.model.shademodel.core.model.chatmodel.ChatErrorResponse;
import stories.model.shademodel.core.model.jobmodel.JobErrorResponse;
import stories.model.shademodel.core.model.jobmodel.JobFeedModelResponse;
import stories.model.shademodel.core.model.jobmodel.JobModel;
import stories.model.shademodel.core.model.jobmodel.JobModelResponse;
import stories.rest.APIFacade;
import stories.rest.responsecheck.ResponseCheckFactory;
import stories.test.BaseBackendTest;
import stories.util.DateTimeUtils;
import stories.util.SoftAssert;
import stories.util.ddto.DdtDataProvider;
import stories.util.ddto.DdtoSet;
import org.testng.ITestContext;

import java.io.IOException;
import java.sql.Array;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

/**
 * Created by wizard on 24.06.2017.
 */
public class JobsTest extends BaseBackendTest {

    private static final String CREATE_JOB = "createJob";
    private static final String REPORT_JOB = "reportJob";
    private static final String REPORT_JOB_NEG = "reportJobNeg";
    public SettingsManager settingsManager;

    public APIFacade accountAPIFacade;
    public int userId2 = 0;
    public int JobId = 0;
    private int TEST_NUM = 1;



    //The DataProvider for the positive test
    @DataProvider(name = "CreateJob")
    private Object[][] createJob(ITestContext context)
            throws IOException {
        settingsManager = SettingsManager.getSettingsManager();
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        DdtDataProvider ddtDataProvider = new DdtDataProvider();
        return ddtDataProvider.ddtProvider(
                context, CREATE_JOB,
                settingsManager.getDdtDataPath());
    }

    @DataProvider(name = "ReportJob")
    private Object[][] reportJob(ITestContext context)
            throws IOException {
        settingsManager = SettingsManager.getSettingsManager();
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        DdtDataProvider ddtDataProvider = new DdtDataProvider();
        return ddtDataProvider.ddtProvider(
                context, REPORT_JOB,
                settingsManager.getDdtDataPath());
    }

    @DataProvider(name = "ReportJobNeg")
    private Object[][] reportJobNeg(ITestContext context)
            throws IOException {
        settingsManager = SettingsManager.getSettingsManager();
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        DdtDataProvider ddtDataProvider = new DdtDataProvider();
        return ddtDataProvider.ddtProvider(
                context, REPORT_JOB_NEG,
                settingsManager.getDdtDataPath());
    }

/*    @AfterClass(dependsOnMethods = "shutdownBaseBackendTest")
    public void deleteUser()
            throws IOException, SQLException {
        if (userId2 != 0) {
            accountAPIFacade.getAccountEndpoint().deleteUser(
                    userId2,
                    Collections.singletonList(ResponseCheckFactory.getStatusCodeCheck(200)),
                    "Deleting user with id: " + userId2 + ".");
        }
    }
*/
    @BeforeClass()
    public void createEmployee() throws IOException, InterruptedException, SQLException {
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
        String email = "test_job_user2_" + rendom + "_@distillery.com";
        newUser2.setEmail(email);
        newUser2.setFirstName("Job");
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

    }


    @Test(description = "Create the job positive test",
            dataProvider = "CreateJob",
        /*    groups = {"accountAPIFacade, test-duration.short", "test-state.working"},*/ priority = 1)
   public void createJobPositive(final Map ddtSetMap)
            throws IOException, InterruptedException, SQLException {
       // testCaseId = TEST_NUM + 1;

        DdtoSet<JobModel> ddtoSet =
                mapper.convertValue(ddtSetMap, new TypeReference<DdtoSet<JobModel>>() {
                });
        testCaseId = ddtoSet.getTestCaseId();
        //Sending the API request to the "/account/signup" endpoint and waiting 200 status code
        final JobModel createJobRequest = ddtoSet.getDto();
        JobModelResponse response = null;

        testCaseId = ddtoSet.getTestCaseId();
        response = (JobModelResponse) accountAPIFacade.getJobEndpoint().createJob(
                createJobRequest, userId2,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(ddtoSet.getStatusCode())),
                testDescription);
        String test_data = ddtoSet.getDescription() + "\ncreate job test\nsetId:"
                + testCaseId + "\n" + ddtoSet.getDescription() + "\n(caseId:" + testCaseId + ")\n[ERROR] ";

        //Make checks about the returned information
        //Add check all fields
        SoftAssert sa = new SoftAssert(test_data);
        String name = response.getTitle();
        Integer salary = response.getSalary();
        //  String salryType = response.getSalaryType();
        Integer jobId = response.getId();
        //  Integer posterId = response.getUserId();
        System.out.printf("Создана Job с ID" + jobId);

        sa.assertTrue(name.equals(response.getTitle()));
        sa.assertTrue(salary.equals(response.getSalary()));
        JobId = jobId;
        sa.assertAll();

    }


    @Test(description = "Create the job positive test",
            dataProvider = "ReportJob",
        /*    groups = {"accountAPIFacade, test-duration.short", "test-state.working"},*/ priority = 2)
    public void reportJobPositive(final Map ddtSetMap)
            throws IOException, InterruptedException, SQLException {
        DdtoSet<JobModel> ddtoSet =
                mapper.convertValue(ddtSetMap, new TypeReference<DdtoSet<JobModel>>() {
                });
        //Sending the API request to the "/account/signup" endpoint and waiting 200 status code
        testCaseId = ddtoSet.getTestCaseId();

        LinkedHashMap<String, Object> tObj = (LinkedHashMap<String, Object>) ddtSetMap;
        LinkedHashMap<String, String> dto = (LinkedHashMap<String, String>) tObj.get("dto");
        String selfId = dto.get("selfId");
        String job = Integer.toString(JobId);

        String reportJob = "{\"selfId\":" + selfId
                + ",\"jobId\":" + job
                + ",\"category\":" + dto.get("category")
                + ",\"comment\":" + dto.get("string")
                + "}";

        Response response = (Response) accountAPIFacade.getJobEndpoint().reportJob(
                reportJob,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(ddtoSet.getStatusCode())),
                testDescription);
        SoftAssert sa = new SoftAssert();
        sa.assertNotNull(response);
        int jobId = JobId;

        String request = "selfId=" + selfId + "&selfUserType=" + dto.get("userType") + "&maxId=" + (jobId + 1) + "&sinceId=" + Integer.toString(jobId - 1);

        List<JobFeedModelResponse> responseJob = accountAPIFacade.getJobEndpoint().feedJob(
                request,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(ddtoSet.getStatusCode())),
                testDescription);


        //Make checks about the returned information
        //Add check all fields
        sa = new SoftAssert();
        sa.assertTrue(responseJob.get(0).getReported() == true);
        sa.assertAll();
    }

    @Test(description = "Create the job positive test",
            dataProvider = "ReportJob",
        /*    groups = {"accountAPIFacade, test-duration.short", "test-state.working"},*/ priority = 3)
    public void reportJobNegative(final Map ddtSetMap)
            throws IOException, InterruptedException, SQLException {

        DdtoSet<JobModel> ddtoSet =
                mapper.convertValue(ddtSetMap, new TypeReference<DdtoSet<JobModel>>() {
                });
        //Sending the API request to the "/account/signup" endpoint and waiting 200 status code
        testCaseId = ddtoSet.getTestCaseId();

        LinkedHashMap<String, Object> tObj = (LinkedHashMap<String, Object>) ddtSetMap;
        LinkedHashMap<String, String> dto = (LinkedHashMap<String, String>) tObj.get("dto");
        String selfId = dto.get("selfId");
        String job = Integer.toString(JobId);

        String reportJobNeg = "{\"selfId\":" + selfId
                + ",\"jobId\":" + job
                + ",\"category\":" + dto.get("category")
                + ",\"comment\":" + dto.get("string")
                + "}";

        ErrorResponse response_error = (ErrorResponse) accountAPIFacade.getJobEndpoint().reportJob(
                reportJobNeg,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(400)),
                testDescription);
        SoftAssert sa = new SoftAssert();

        sa.assertTrue(response_error.getErrorCode() == ddtoSet.getStatusCode());
        sa.assertTrue(response_error.getErrorMessage().equals(dto.get("errorMessage")));
        sa.assertAll();
    }
}
