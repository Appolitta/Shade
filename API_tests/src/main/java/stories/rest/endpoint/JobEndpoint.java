package stories.rest.endpoint;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jayway.restassured.response.Response;
import stories.model.shademodel.core.model.chatmodel.ChatErrorResponse;
import stories.model.shademodel.core.model.jobmodel.*;
import stories.rest.Rest;
import stories.rest.responsecheck.ResponseCheck;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import stories.util.SoftAssert;

/**
 * Created by wizard on 24.06.2017.
 */
public class JobEndpoint extends AbstractEndpoint  {
    public JobEndpoint(Rest rest) {
        super(rest, "job");
    }


    //API method for registering a new user

    public Object createJob(final JobModel request,
                             final Integer userId,
                             final List<ResponseCheck> responseChecks,
                             final String description) throws IOException
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Response response =  post(
                "?userId="  + Integer.toString(userId),
                headers,
                request,
                false,
                responseChecks,
                description);
        if (response.getStatusCode() == 200) {
            return responseMapper.readValue(response.asString(), JobModelResponse.class);
        } else {
            return responseMapper.readValue(response.asString(), JobErrorResponse.class);
        }
    }

    public List feedJob (final String request,
                           final List<ResponseCheck> responseChecks,
                           final String description) throws IOException {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        String req = "/feed?" + request;
        Response response = get(req,
                headers,
                responseChecks,
                description);
        if (response.getStatusCode() == 200) {
            try {
                FileWriter file = new FileWriter("test.json");
                file.write(response.asString());
                file.flush();
                file.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if (response.getStatusCode() == 200) {
            List<JobFeedModelResponse> jb = responseMapper.readValue(response.asString(), new TypeReference<List<JobFeedModelResponse>>() {
            });
            return jb;
            //responseMapper.readValue(response.asString(),  List.class);
        } else {
            List<JobErrorResponse> jb = responseMapper.readValue(response.asString(), new TypeReference<List<JobFeedModelResponse>>() {
            });
            return jb;
        }
    }

    public Object getJob( final Integer jobId,
                            final List<ResponseCheck> responseChecks,
                            final String description) throws IOException
    {   String request = "";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Response response =  post(
                "/*"  + Integer.toString(jobId),
                headers,
                request,
                false,
                responseChecks,
                description);
        if (response.getStatusCode() == 200) {
            return responseMapper.readValue(response.asString(), JobModelResponse.class);
        } else {
            return responseMapper.readValue(response.asString(), JobErrorResponse.class);
        }
    }

    public Response reportJob(final String request,

                            final List<ResponseCheck> responseChecks,
                            final String description) throws IOException
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Response response =  post(
                "/reportjob",
                headers,
                request,
                false,
                responseChecks,
                description);
     /*   if (response.getStatusCode() == 200) {
            return responseMapper.readValue(response.asString(), Response.class);
        } else {
            return responseMapper.readValue(response.asString(), Response.class);
        }*/
        if (response.getStatusCode() == 200) {
            return  response;
        } else {
            return response;
        }
    }



        public void checkJobNull(final JobFeedModelResponse job){
            SoftAssert sa = new SoftAssert();
            sa.assertNotNull(job.getId());
            sa.assertNotNull(job.getName());
            sa.assertNotNull(job.getCategoryId());
            sa.assertNotNull(job.getLogoUrl().getUrlToFolder());
            sa.assertNotNull(job.getLogoUrl().getFileName());
            sa.assertNotNull(job.getLocation().getAddress());
            sa.assertNotNull(job.getLocation().getId());
            sa.assertNotNull(job.isSaved());
            sa.assertNotNull(job.getPoster().getId());
            sa.assertNotNull(job.getPoster().getFirstName());
            sa.assertNotNull(job.getPoster().getLastName());
  //          sa.assertNotNull(job.getPoster().getRaitingInfo().getRating());
  //          sa.assertNotNull(job.getPoster().getRaitingInfo().getNumberOfReviews());
//
        }
}