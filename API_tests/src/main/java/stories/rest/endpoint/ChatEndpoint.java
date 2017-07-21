package stories.rest.endpoint;
import com.fasterxml.jackson.core.type.TypeReference;
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
import stories.util.SoftAssert;
/**
 * Created by wizard on 19.07.2017.
 */
public class ChatEndpoint extends AbstractEndpoint  {
    public ChatEndpoint(Rest rest) {
        super(rest, "/Chat");
    }

  /*  public  boolean Login (String login, String password){{
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

    public String sentMessage(final JobModel request,
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


*/

}