package stories.UserStory;

import com.fasterxml.jackson.core.type.TypeReference;
import org.testng.annotations.AfterClass;
import stories.managers.SettingsManager;
import stories.model.shademodel.core.Roles;
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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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
    private SettingsManager settingsManager;

    private APIFacade accountAPIFacade;

    private int TEST_NUM = 1;

    @BeforeClass

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
        Integer userId = 110;
        response = (JobModelResponse) accountAPIFacade.getJobEndpoint().createJob(
                createJobRequest, userId,
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
