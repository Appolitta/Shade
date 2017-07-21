package stories.UserStory;

import com.fasterxml.jackson.core.type.TypeReference;
import org.testng.annotations.*;
import stories.managers.SettingsManager;
import stories.model.shademodel.core.Roles;
import stories.model.shademodel.core.model.accountmodel.UserModel;
import stories.model.shademodel.core.model.accountmodel.UserModelResponse;
import stories.model.shademodel.core.model.jobmodel.JobFeedModelResponse;
import stories.model.shademodel.core.model.jobmodel.JobModel;
import stories.model.shademodel.core.model.jobmodel.JobModelResponse;
import stories.model.shademodel.core.model.jobmodel.JobErrorResponse;
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

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
/**
 * Created by wizard on 24.06.2017.
 */
public class JobTest extends BaseBackendTest {

    private static final String CREATE_JOB = "createJob";
    public SettingsManager settingsManager;

    public APIFacade accountAPIFacade;
    public int userId2 = 0;
    private int TEST_NUM = 1;



    //The DataProvider for the positive test
    @DataProvider(name = "CreateJob")
    private Object[][] createJob(ITestContext context)
            throws IOException {
        settingsManager = SettingsManager.getSettingsManager();
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        DdtDataProvider ddtDataProvider = new DdtDataProvider();
        return  ddtDataProvider.ddtProvider(
                context, CREATE_JOB,
                settingsManager.getDdtDataPath());
    }

    @BeforeClass
    @AfterClass(dependsOnMethods = "shutdownBaseBackendTest")
    public void deleteUser()
            throws IOException, SQLException {
        if (userId2 != 0) {
            accountAPIFacade.getAccountEndpoint().deleteUser(
                    userId2,
                    Collections.singletonList(ResponseCheckFactory.getStatusCodeCheck(200)),
                    "Deleting user with id: " + userId2 + ".");
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
        newUser2.setEmail("test_job_user2@distillery.com");
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
        System.out.println(" что-нибудь");
        userId2 = response.getAccess_token() != null ? response.getShadeUserModelResponse().getId() : 0;

    }

    @Test(description = "Create the job positive test",
            dataProvider = "CreateJob",
            groups = {"accountAPIFacade, test-duration.short", "test-state.working"}, priority = 1)
    public void createJobPositive(final Map ddtSetMap)
            throws IOException, SQLException {
        testCaseId = TEST_NUM + 1;

        DdtoSet<JobModel> ddtoSet =
                mapper.convertValue(ddtSetMap, new TypeReference<DdtoSet<JobModel>>() {
                });
        //Sending the API request to the "/account/signup" endpoint and waiting 200 status code
        final JobModel createJobRequest = ddtoSet.getDto();
        JobModelResponse response = null;


        response = (JobModelResponse) accountAPIFacade.getJobEndpoint().createJob(
                createJobRequest, userId2,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(ddtoSet.getStatusCode())),
                testDescription);
        String test_data =  ddtoSet.getDescription() + "\ncreate job test\nsetId:"
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

   //  SoftAssert sa = new SoftAssert(test_data);
     //   sa.assertTrue(response.getAccess_token() != null, test_data);
       sa.assertTrue(name.equals(response.getTitle()));
       sa.assertTrue(salary.equals(response.getSalary()));
     //  sa.assertTrue(salryType.equals(response.getSalaryType()));
    //   jobId = response.getAccess_token() != null ? response.getId() : 0;
        sa.assertAll();
    }


}
