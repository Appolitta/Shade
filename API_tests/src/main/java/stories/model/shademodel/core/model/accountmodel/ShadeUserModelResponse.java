package stories.model.shademodel.core.model.accountmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import stories.model.shademodel.core.model.chatmodel.ChatBase;
import stories.model.shademodel.core.model.usermodel.UserErrorResponse;

import java.util.List;

/**
 * Created by weezlabs on 4/3/17.
 */
public class ShadeUserModelResponse extends UserBase {

    @JsonProperty("type")
    private String type;

    @JsonProperty("chats")
    private List<ChatBase> chats;

    @JsonProperty("verificationOptions")
    private List<String> verificationOptions;

    @JsonProperty("errorResponse")
    private UserErrorResponse userErrorResponse;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public UserErrorResponse getUserErrorResponse() {
        return userErrorResponse;
    }

    public void setUserErrorResponse(UserErrorResponse userErrorResponse) {
        this.userErrorResponse = userErrorResponse;
    }
}
