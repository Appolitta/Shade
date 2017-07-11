package stories.model.shademodel.core.model.usermodel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by weezlabs on 4/18/17.
 */
public class UserErrorResponse {

    @JsonProperty("errorCode")
    private int errorCode;

    @JsonProperty("errorMessage")
    private String errorMessage;

    @JsonProperty("invalidFields")
    private List<String> invalidFields;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<String> getInvalidFields() {
        return invalidFields;
    }

    public void setInvalidFields(List<String> invalidFields) {
        this.invalidFields = invalidFields;
    }

}
