package stories.UserStory;

import com.fasterxml.jackson.core.type.TypeReference;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import stories.db.DBFacade;
import stories.managers.SettingsManager;
import stories.model.job.Job;
import stories.model.shademodel.core.model.jobmodel.JobModel;
import stories.model.shademodel.core.model.jobmodel.JobFeedModelResponse;

import stories.model.shademodel.core.model.jobmodel.JobModelResponse;
import stories.rest.APIFacade;
import stories.rest.responsecheck.ResponseCheckFactory;
import stories.test.BaseBackendTest;
import stories.util.SoftAssert;
import stories.util.ddto.DdtDataProvider;
import stories.util.ddto.DdtoSet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;


/**
 * Created by wizard on 04.07.2017.
 */
public class JobFeedTest extends BaseBackendTest {

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


        DdtoSet<JobFeedModelResponse> ddtoSet =
                mapper.convertValue(ddtSetMap, new TypeReference<DdtoSet<JobFeedModelResponse>>() {
                });


        LinkedHashMap<String, Object> tObj = (LinkedHashMap<String, Object>) ddtSetMap;
        LinkedHashMap<String, String> dto = (LinkedHashMap<String, String>) tObj.get("dto");

        String feedJobRequest = "";
        String sqlRequest = "";
        int count = 0;

        if (dto.get("searchingPhrase") != null) {
            String name = dto.get("searchingPhrase");
            feedJobRequest = feedJobRequest + "searchingPhrase" + "=" + name + "&";
            sqlRequest = sqlRequest + "\"Name\"" + " = '" + name + "'" + " and ";
            System.out.println("searchingPhrase" + "=" + name);
        }

        if (dto.get("categoryId") != null) {
            String categories = dto.get("categoryId");
            feedJobRequest = feedJobRequest + "categories" + "=" + categories + "&";
            sqlRequest = sqlRequest + "\"CategoryId\"" + " = " + categories + " and ";
            System.out.println("categories" + "=" + categories);
        }

        if (dto.get("startDate") != null) {
            String startDate = dto.get("startDate");
            feedJobRequest = feedJobRequest + "startDate" + "=" + startDate + "&";
            sqlRequest = sqlRequest + "\"StartDate\"" + " > '" + startDate + "'" + " and ";
            System.out.println("startDate" + "=" + startDate);
        }

        if (dto.get("salaryMin") != null) {
            String salaryMin = dto.get("salaryMin");
            feedJobRequest = feedJobRequest + "salaryMin" + "=" + salaryMin + "&";
            sqlRequest = sqlRequest + "\"Salary\"" + " >= " + salaryMin + " and ";
        }

        if (dto.get("recordsCount") != null) {
            String recordsCount = dto.get("recordsCount");
            feedJobRequest = feedJobRequest + "recordsCount" + "=" + recordsCount + "&";
            count = Integer.parseInt(recordsCount);
        }

        if (dto.get("maxId") != null) {
            String maxId = dto.get("maxId");
            feedJobRequest = feedJobRequest + "maxId" + "=" + maxId + "&";
            sqlRequest = sqlRequest + "\"Id\"" + " < " + maxId + " and ";
        }

        if (dto.get("sinceId") != null) {
            String sinceId = dto.get("sinceId");
            feedJobRequest = feedJobRequest + "sinceId" + "=" + sinceId + "&";
            sqlRequest = sqlRequest + "\"Id\"" + " > " + sinceId + " and " ;
        }

        if (dto.get("employerRating") != null) {
            String employerRating = dto.get("employerRating");
            feedJobRequest = feedJobRequest + "employerRating" + "=" + employerRating;
          //  sqlRequest = sqlRequest + "\"employerRating\"" + " > " + employerRating + "";

        }

            //deleted last @and
        String[] words = sqlRequest.split(" ");
        words[words.length - 1] = ""; //there is last "and"
        String sqlRequest2 = String.join(" ", words);
        if (count == 0) {
            if (dto.get("employerRating") != null) {
                if(sqlRequest2.length() != 0)
                    sqlRequest2 = " and " + sqlRequest2;
                else sqlRequest2 = "    ";
                count = testDBFacade.JobCountForRating(dto.get("employerRating"), sqlRequest2);
            }
            else
                count = testDBFacade.JobCount(sqlRequest2);
        }



/*        String[] words2 = feedJobRequest.split(" ");
    //    if (equals(words2[words2.length-1] == "&")) {
        words2[words2.length - 1] = "";
        feedJobRequest = String.join(" ", words2);
  //     }
*/
         List<JobFeedModelResponse> response =  new ArrayList<>();

         response = accountAPIFacade.getJobEndpoint().feedJob(
                feedJobRequest,
                Collections.singletonList(
                        ResponseCheckFactory.getStatusCodeCheck(ddtoSet.getStatusCode())),
                testDescription);

         String test_data = ddtoSet.getDescription() + "\ncreate job test\nsetId:"
                    + testCaseId + "\n" + ddtoSet.getDescription() + "\n(caseId:" + testCaseId + ")\n[ERROR] ";

           //-----------Check response------------
         SoftAssert sa = new SoftAssert(test_data);
         if (count>100) count = 100;
         if (count == response.size()) {
             System.out.println("Return valid count of job = " + count);
             int num;
             int index;

              //by name
             if (dto.get("searchingPhrase") != null) {
                 sa.assertTrue(response.size() > 0, "Empty  response after request by name!");
                 num = response.size();
                 index = 0;
                 while (num > 0) {
                     sa.assertNotNull(response.get(index), "Null count parameters!");
                     JobFeedModelResponse job = response.get(index);
                     response.get(index);
                     sa.assertTrue(job.getName().equals(dto.get("searchingPhrase")));
                     num --;
                     index ++;
                     System.out.println("All good of searchingPhrase");
                 }
             }

              //by categoryId
             if (dto.get("categoryId") != null) {
                 sa.assertTrue(response.size() > 0, "Empty  response after request by categoryId!");
                 num = response.size();
                 index = 0;
                 while (num > 0) {
                       sa.assertNotNull(response.get(index), "Null count parameters!");
                       JobFeedModelResponse job = response.get(index);
                       response.get(index);
                       sa.assertTrue(job.getCategoryId() == Integer.valueOf(dto.get("categoryId")));
                       num--;
                       index++;
                       System.out.println("All good of categoryId");
                   }
               }
               //by startDate
             if (dto.get("startDate") != null) {
                   sa.assertTrue(response.size() > 0, "Empty  response after request by startDate!");
                   num = response.size();
                   index = 0;
                   while (num > 0) {
                       sa.assertNotNull(response.get(index), "Null count parameters!");
                       JobFeedModelResponse job = response.get(index);
                       response.get(index);

                       sa.assertTrue(Integer.valueOf((dto.get("startDate"))) <= Integer.valueOf(job.getStartDate()));
                       num--;
                       index++;
                   }
               }


               //by salaryMin
             if (dto.get("salaryMin") != null) {
                   sa.assertTrue(response.size() > 0, "Empty  response after request by salaryMin!");
                   num = response.size();
                   index = 0;
                   while (num > 0) {
                       sa.assertNotNull(response.get(index), "Null count parameters!");
                       JobFeedModelResponse job = response.get(index);
                       response.get(index);
                       sa.assertTrue(job.getSalary() >= Integer.valueOf(dto.get("salaryMin")));
                       num--;
                       index++;
                       System.out.println("All good of categoryId");
                   }
             }

               //by "recordsCount"
             if (dto.get("recordsCount") != null) {
                   sa.assertTrue(response.size() > 0, "Empty  response after request recordsCount!");
                   sa.assertTrue ( response.size() == Integer.valueOf(dto.get("recordsCount")));
             }


             if (dto.get("maxId") != null) {
                   sa.assertTrue(response.size() > 0, "Empty  response after request by maxId!");
                   num = response.size();
                   index = 0;
                   while (num > 0) {
                       sa.assertNotNull(response.get(index), "Null count parameters!");
                       JobFeedModelResponse job = response.get(index);
                       response.get(index);
                       sa.assertTrue(job.getId() < Integer.valueOf(dto.get("maxId")));
                       num--;
                       index++;
                       System.out.println("All good of maxId");
                   }
             }


             if (dto.get("sinceId") != null) {
                   sa.assertTrue(response.size() > 0, "Empty  response after request by sinceId!");
                   num = response.size();
                   index = 0;
                   while (num > 0) {
                       sa.assertNotNull(response.get(index), "Null count parameters!");
                       JobFeedModelResponse job = response.get(index);
                       response.get(index);
                       sa.assertTrue(job.getId() > Integer.valueOf(dto.get("sinceId")));
                       num--;
                       index++;
                       System.out.println("All good of sinceId");
                   }
             }

             if (dto.get("employerRating") != null) {
                 sa.assertTrue(response.size() > 0, "Empty  response after request by employerRating!");
                 num = response.size();
                 index = 0;
                 while (num > 0) {
                     sa.assertNotNull(response.get(index), "Null count parameters!");
                     JobFeedModelResponse job = response.get(index);
                     response.get(index);
                     sa.assertTrue(job.getPoster().getRaitingInfo().getRating() >= Integer.valueOf(dto.get("employerRating")));
                     num--;
                     index++;
                     System.out.println("All good of employerRating");
                 }
             }


           }
           else {
             sa.assertTrue(count == response.size());
             System.out.println("!!!!!!!!!!!!!!Return invalid count of job!!!!!!!!!!!!!!");
         }
        sa.assertAll();


        }


 //   }


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

