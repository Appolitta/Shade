package stories.model.shademodel.core.form;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FieldOptionModel {
    @JsonProperty("value")
    private String value;

    @JsonProperty("text")
    private String text;

    @JsonProperty("additional")
    private String additional;

    @JsonProperty("code")
    private String code;

    @JsonProperty("childOptionSet")
    private String childOptionSet;

    @JsonProperty("orderNumber")
    private Integer orderNumber;

    @JsonProperty("isDeleted")
    private Boolean isDeleted;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getChildOptionSet() {
        return childOptionSet;
    }

    public void setChildOptionSet(String childOptionSet) {
        this.childOptionSet = childOptionSet;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
