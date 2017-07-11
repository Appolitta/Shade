package stories.model.shademodel.core.form;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidationFailure {
    @JsonProperty("attemptedValue")
    private Object attemptedValue;

    @JsonProperty("previousValue")
    private Object previousValue;

    @JsonProperty("propertyName")
    private String propertyName;

    @JsonProperty("errorMessage")
    private String errorMessage;

    @JsonProperty("customState")
    private Object customState;

    @JsonProperty("validatorName")
    private String validatorName;

    @JsonProperty("isMandatory")
    private Boolean isMandatory;

    public Object getAttemptedValue() {
        return attemptedValue;
    }

    public void setAttemptedValue(Object attemptedValue) {
        this.attemptedValue = attemptedValue;
    }

    public Object getPreviousValue() {
        return previousValue;
    }

    public void setPreviousValue(Object previousValue) {
        this.previousValue = previousValue;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getCustomState() {
        return customState;
    }

    public void setCustomState(Object customState) {
        this.customState = customState;
    }

    public String getValidatorName() {
        return validatorName;
    }

    public void setValidatorName(String validatorName) {
        this.validatorName = validatorName;
    }

    public Boolean getMandatory() {
        return isMandatory;
    }

    public void setMandatory(Boolean mandatory) {
        isMandatory = mandatory;
    }
}
