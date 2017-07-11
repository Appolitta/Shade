package stories.model.shademodel.core.form;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ValidationResult {
    @JsonProperty("errors")
    private List<ValidationFailure> errors;

    @JsonProperty("isValid")
    private Boolean isValid;

    @JsonProperty("isFatal")
    private Boolean isFatal;

    public List<ValidationFailure> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationFailure> errors) {
        this.errors = errors;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public Boolean getFatal() {
        return isFatal;
    }

    public void setFatal(Boolean fatal) {
        isFatal = fatal;
    }
}
