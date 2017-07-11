package stories.rest.endpoint;

import com.jayway.restassured.response.Response;
import stories.model.shademodel.core.model.jobmodel.*;
import stories.rest.Rest;
import stories.rest.responsecheck.ResponseCheck;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileWriter;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by wizard on 24.06.2017.
 */
public class JobEndpoint extends AbstractEndpoint  {
    public JobEndpoint(Rest rest) {
        super(rest, "/job");
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
                           final String description) throws IOException
    {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        String req = "/feed?" + request;
        Response response =  get(req,
                headers,
                responseChecks,
                description );
        if (response.getStatusCode() == 200) {
            try{
                FileWriter file = new FileWriter("test.json");
                file.write(response.asString());
                file.flush();
                file.close();
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }

        if (response.getStatusCode() == 200) {
            List<JobFeedModelResponse> jb = responseMapper.readValue(response.asString(),  List.class);
            return jb;
            //responseMapper.readValue(response.asString(),  List.class);
        } else {
            List<JobErrorResponse> jb = responseMapper.readValue(response.asString(),  List.class);
            return jb;
        }


    }
}