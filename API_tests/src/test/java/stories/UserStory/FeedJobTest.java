package stories.UserStory;

import com.fasterxml.jackson.core.type.TypeReference;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import stories.db.DBFacade;
import stories.managers.SettingsManager;
import stories.model.shademodel.core.model.jobmodel.JobModel;
import stories.model.shademodel.core.model.jobmodel.JobFeedModelResponse;
import stories.rest.APIFacade;
import stories.rest.responsecheck.ResponseCheckFactory;
import stories.test.BaseBackendTest;
import stories.util.SoftAssert;
import stories.util.ddto.DdtDataProvider;
import stories.util.ddto.DdtoSet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

/**
 * Created by wizard on 04.07.2017.
 */
public class FeedJobTest extends BaseBackendTest {

    private SettingsManager settingsManager;
    private APIFacade accountAPIFacade;
    private DBFacade testDBFacade;
    private static final String FEED_JOB = "FeedJob";

    @BeforeClass

    //The DataProvider for the positive test
    @DataProvider(name = "FeedJob")
    private Object[][] FeedJob(ITestContext context)
            throws IOException {
        settingsManager = SettingsManager.getSettingsManager();
        testDBFacade = new DBFacade( settingsManager.getDefaultBackendSettings());
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        DdtDataProvider ddtDataProvider = new DdtDataProvider();
        return  ddtDataProvider.ddtProvider(
                context, FEED_JOB,
                settingsManager.getDdtDataPath());
    }



    @Test (description = "Feed the job positive test",
            dataProvider = "FeedJob",
            groups = {"accountAPIFacade, test-duration.short", "test-state.working"}, priority = 1)

    public void feedJobPositive(ITestContext context, final Map ddtSetMap) throws IOException, SQLException{

        DdtoSet<JobModel> ddtoSet =
                mapper.convertValue(ddtSetMap, new TypeReference<DdtoSet<JobModel>>() {
                });
        //Sending the API request to the "/account/signup" endpoint and waiting 200 status code

        Object[][] jobData = FeedJob(context);
        String request="";
        for (Object[] obj : jobData) {
            LinkedHashMap<String, Object> tObj = (LinkedHashMap<String, Object>) obj[0];
            LinkedHashMap<String, String> dto = (LinkedHashMap<String, String>) tObj.get("dto");
            //str[3];

            String feedJobRequest = "";
            String sqlRequest = "";
            int count = 0;

            if (dto.get("searchingPhrase") != null) {
                String name = dto.get("searchingPhrase");
                feedJobRequest = feedJobRequest + "searchingPhrase" + "=" + name;
                sqlRequest = sqlRequest + "\"Name\"" + " = '" + name + "'";
                System.out.println("searchingPhrase" + "=" + name);
             }

            if (dto.get("categoryId") != null) {
                String categories = dto.get("categoryId");
                feedJobRequest = feedJobRequest + "categories" + "=" + categories;
                sqlRequest = sqlRequest + "\"CategoryId\"" + " = " + categories + "";
                System.out.println("categories" + "=" + categories);

            }

            if (dto.get("startDate") != null) {
                String startDate = dto.get("startDate");
                feedJobRequest = feedJobRequest + "startDate" + "=" + startDate;
                sqlRequest = sqlRequest + "\"StartDate\"" + " > '" + startDate + "'";
                System.out.println("startDate" + "=" + startDate);
            }


            if (dto.get("salaryMin") != null) {
                String salaryMin = dto.get("salaryMin");
                feedJobRequest = feedJobRequest + "salaryMin" + "=" + salaryMin;
                sqlRequest = sqlRequest + "\"Salary\"" + " > " + salaryMin + "";
            }

           if (dto.get("recordsCount") != null) {
                String recordsCount = dto.get("recordsCount");
                feedJobRequest = feedJobRequest + "recordsCount" + "=" + recordsCount;
                count = Integer.parseInt(recordsCount);
            }

            if (dto.get("maxId") != null) {
                String maxId = dto.get("maxId");
                feedJobRequest = feedJobRequest + "maxId" + "=" + maxId;
                sqlRequest = sqlRequest + "\"Id\"" + " < " + maxId + "";
            }

            if (dto.get("sinceId") != null) {
                String sinceId = dto.get("sinceId");
                feedJobRequest = feedJobRequest + "sinceId" + "=" + sinceId;
                sqlRequest = sqlRequest + "\"Id\"" + " > " + sinceId + "";
            }

            if (count == 0) {
                count = testDBFacade.JobCount(sqlRequest);
            }


           List<JobFeedModelResponse> response = null;
       //     Integer userId = 110;
           response = (List<JobFeedModelResponse>) accountAPIFacade.getJobEndpoint().feedJob(
                    feedJobRequest,
                    Collections.singletonList(
                            ResponseCheckFactory.getStatusCodeCheck(ddtoSet.getStatusCode())),
                    testDescription);





  /*         if (count == response.size()){
                String test_data = ddtoSet.getDescription() + "\ncreate job test\nsetId:"
                        + testCaseId + "\n" + ddtoSet.getDescription() + "\n(caseId:" + testCaseId + ")\n[ERROR] ";
                SoftAssert sa = new SoftAssert(test_data);
                String name = response.getTitle();
                Integer salary = response.getSalary();
                //  String salryType = response.getSalaryType();
                Integer jobId = response.getId();
                //  Integer posterId = response.getUserId();
                System.out.printf("Создана Job с ID" + jobId);

               }
            else System.out.printf("Return invalid count of job");
*/

        }


    }


}

/*
/////////////////////!!!!!!!!!!хз как!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            if (dto.get("distanceMax") != null) {
                    String distanceMax = dto.get("distanceMax");
                    feedJobRequest = feedJobRequest + "distanceMax" + "=" + distanceMax;
                    }

                    if (dto.get("longitude") != null) {

                    String ownLatitude = dto.get("longitude");
                    feedJobRequest = feedJobRequest + "ownLocation.longitude" + "=" + ownLatitude;
                    }

                    if (dto.get("longitude") != null) {
                    String ownLongitude = dto.get("latitude");
                    feedJobRequest = feedJobRequest + "ownLocation.longitude" + "=" + ownLongitude;
                    }
///////////////////////////////////////////////////////////////

*/
/*
        ///сложный зарос с выборкой из 2х таблиц
        if (dto.get("employerRating") != null) {
            String employerRating = dto.get("employerRating");
            feedJobRequest = feedJobRequest + "employerRating" + "=" + employerRating;
            sqlRequest = sqlRequest + "\"employerRating\"" + " > " + employerRating + "";

        }
        //////////
*/
