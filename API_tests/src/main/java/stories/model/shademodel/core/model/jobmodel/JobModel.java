package stories.model.shademodel.core.model.jobmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashMap;
import java.util.List;




/**
 * Created by weezlabs on 4/3/17.
 */
public class JobModel extends JobBase{


    @JsonProperty("startDate")
    private String startDate;

    @JsonProperty("endDate")
    private String endDate;

    @JsonProperty("startTime")
    private String startTime;

    @JsonProperty("endTime")
    private String endTime;

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("errorCode")
    private int errorCode;

    @JsonProperty("errorMessage")
    private String errorMessage;

    @JsonProperty("invalidFields")
    private List<String> invalidFields;

    @JsonProperty("location")
    private Location location;

    @JsonProperty("summary")
    private String summary;

    @JsonProperty("description")
    private String description;

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndDate() {
    return endDate;
}

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getUserId(){ return userId; }

    public void setUserId (Integer PosterId) { this.userId = userId; }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}