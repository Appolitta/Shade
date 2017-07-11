package stories.model.shademodel.core.model.accountmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import stories.model.shademodel.core.model.usermodel.UserProfileBase;

public class UserModelResponse extends UserProfileBase {
    @JsonProperty("access_token")
    private String access_token;

    @JsonProperty("shadeUser")
    private ShadeUserModelResponse shadeUserModelResponse;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public ShadeUserModelResponse getShadeUserModelResponse() {
        return shadeUserModelResponse;
    }

    public void setShadeUserModelResponse(ShadeUserModelResponse shadeUserModelResponse) {
        this.shadeUserModelResponse = shadeUserModelResponse;
    }
}
