package stories.model.shademodel.core.model.jobmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import stories.model.shademodel.core.model.chatmodel.ChatBase;
import stories.model.shademodel.core.model.jobmodel.JobErrorResponse;


import java.util.List;

/**
 * Created by wizard on 24.06.2017.
 */
public class ShadeJobModelResponse extends JobModel {

    @JsonProperty("chats")
    private List<ChatBase> chats;

    @JsonProperty("verificationOptions")
    private List<String> verificationOptions;

    @JsonProperty("errorResponse")
    private JobErrorResponse jobErrorResponse;


    @JsonProperty("salaryType")
    private Integer salaryType;


    public Integer getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(Integer salaryType){
        this.salaryType = salaryType;
    }


    public List<ChatBase> getChats() {
        return chats;
    }

    public void setChats(List<ChatBase> chats) {
        this.chats = chats;
    }

    public List<String> getVerificationOptions() {
        return verificationOptions;
    }

    public void setVerificationOptions(List<String> verificationOptions) {
        this.verificationOptions = verificationOptions;
    }

    public JobErrorResponse getJobErrorResponse() {
        return jobErrorResponse;
    }

    public void setJobErrorResponse(JobErrorResponse jobErrorResponse) {
        this.jobErrorResponse = jobErrorResponse;
    }

}
