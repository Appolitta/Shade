package stories.model.shademodel.core.model.chatmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import stories.model.shademodel.core.model.accountmodel.UserBase;
import stories.model.shademodel.core.model.accountmodel.UserModel;
import stories.model.shademodel.core.model.jobmodel.JobBase;
import stories.model.shademodel.core.model.jobmodel.JobModel;

/**
 * Created by wizard on 24.07.2017.
 */
public class ChatResponse {

    @JsonProperty("chat")
    private ChatBase chat;

    @JsonProperty("jobInfo")
    private JobBase jobInfo;

    @JsonProperty("opponentInfo")
    private UserBase opponentInfo;

    public JobBase getJobInfo() {
        return jobInfo;
    }

    public void setJobInfo(JobBase jobInfo) {
        this.jobInfo = jobInfo;
    }

    public ChatBase getChat() {

        return chat;
    }

    public void setChat(ChatBase chat) {
        this.chat = chat;
    }

    public UserBase getOpponentInfo() {
        return opponentInfo;
    }

    public void setOpponentInfo(UserBase opponentInfo) {
        this.opponentInfo = opponentInfo;
    }
}
