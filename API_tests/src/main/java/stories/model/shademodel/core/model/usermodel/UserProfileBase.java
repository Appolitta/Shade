package stories.model.shademodel.core.model.usermodel;

import com.fasterxml.jackson.annotation.JsonProperty;


public class UserProfileBase extends UserBase {
    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("middleName")
    private String middleName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("location")
    private String location;

    @JsonProperty("positionText")
    private String positionText;

    // Nullable.
    @JsonProperty("positionId")
    private Integer positionId;

    // Nullable.
    @JsonProperty("managementId")
    private Integer managementId;

    // Nullable.
    @JsonProperty("departmentId")
    private Integer departmentId;

    // Nullable.
    @JsonProperty("groupId")
    private Integer groupId;

    // Nullable.
    @JsonProperty("ntcId")
    private Integer ntcId;

    // Nullable.
    @JsonProperty("organizationId")
    private Integer organizationId;

    // Nullable.
    @JsonProperty("pictureId")
    private Integer pictureId;

    @JsonProperty("pictureUrl")
    private String pictureUrl;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPositionText() {
        return positionText;
    }

    public void setPositionText(String positionText) {
        this.positionText = positionText;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getManagementId() {
        return managementId;
    }

    public void setManagementId(Integer managementId) {
        this.managementId = managementId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getNtcId() {
        return ntcId;
    }

    public void setNtcId(Integer ntcId) {
        this.ntcId = ntcId;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Integer getPictureId() {
        return pictureId;
    }

    public void setPictureId(Integer pictureId) {
        this.pictureId = pictureId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
