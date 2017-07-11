package stories.model.shademodel.core.workflow;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PackageFlowModel {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("withoutCoveringLetter")
    private Boolean withoutCoveringLetter;

    @JsonProperty("coveringLetterIncomingNumber")
    private String coveringLetterIncomingNumber;

    // Nullable.
    @JsonProperty("coveringLetterIncomingDate")
    private String coveringLetterIncomingDate;

    @JsonProperty("coveringLetterOutgoingNumber")
    private String coveringLetterOutgoingNumber;

    // Nullable.
    @JsonProperty("coveringLetterOutgoingDate")
    private String coveringLetterOutgoingDate;

    // Nullable.
    @JsonProperty("documentPackageSourceId")
    private Integer documentPackageSourceId;

    @JsonProperty("documentPackageSourceName")
    private String documentPackageSourceName;

    // Nullable.
    @JsonProperty("organizationId")
    private Integer organizationId;

    @JsonProperty("organizationName")
    private String organizationName;

    // Nullable.
    @JsonProperty("returnDocOrganizationId")
    private Integer returnDocOrganizationId;

    @JsonProperty("returnDocOrganizationName")
    private String returnDocOrganizationName;

    // Nullable.
    @JsonProperty("controlledByUserId")
    private Integer controlledByUserId;

    @JsonProperty("controlledByUserName")
    private String controlledByUserName;

    @JsonProperty("createdAt")
    private String createdAt;

    // Nullable.
    @JsonProperty("registeredAt")
    private String registeredAt;

    // Nullable.
    @JsonProperty("completedAt")
    private String completedAt;

    @JsonProperty("status")
    private String status;

    @JsonProperty("statusString")
    private String statusString;

    @JsonProperty("isReady")
    private Boolean isReady;

    @JsonProperty("documentsCount")
    private Integer documentsCount;

    @JsonProperty("readyDocumentsCount")
    private Integer readyDocumentsCount;

    @JsonProperty("completedDocumentsCount")
    private Integer completedDocumentsCount;

    @JsonProperty("returnedDocumentsCount")
    private Integer returnedDocumentsCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getWithoutCoveringLetter() {
        return withoutCoveringLetter;
    }

    public void setWithoutCoveringLetter(Boolean withoutCoveringLetter) {
        this.withoutCoveringLetter = withoutCoveringLetter;
    }

    public String getCoveringLetterIncomingNumber() {
        return coveringLetterIncomingNumber;
    }

    public void setCoveringLetterIncomingNumber(String coveringLetterIncomingNumber) {
        this.coveringLetterIncomingNumber = coveringLetterIncomingNumber;
    }

    public String getCoveringLetterIncomingDate() {
        return coveringLetterIncomingDate;
    }

    public void setCoveringLetterIncomingDate(String coveringLetterIncomingDate) {
        this.coveringLetterIncomingDate = coveringLetterIncomingDate;
    }

    public String getCoveringLetterOutgoingNumber() {
        return coveringLetterOutgoingNumber;
    }

    public void setCoveringLetterOutgoingNumber(String coveringLetterOutgoingNumber) {
        this.coveringLetterOutgoingNumber = coveringLetterOutgoingNumber;
    }

    public String getCoveringLetterOutgoingDate() {
        return coveringLetterOutgoingDate;
    }

    public void setCoveringLetterOutgoingDate(String coveringLetterOutgoingDate) {
        this.coveringLetterOutgoingDate = coveringLetterOutgoingDate;
    }

    public Integer getDocumentPackageSourceId() {
        return documentPackageSourceId;
    }

    public void setDocumentPackageSourceId(Integer documentPackageSourceId) {
        this.documentPackageSourceId = documentPackageSourceId;
    }

    public String getDocumentPackageSourceName() {
        return documentPackageSourceName;
    }

    public void setDocumentPackageSourceName(String documentPackageSourceName) {
        this.documentPackageSourceName = documentPackageSourceName;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Integer getReturnDocOrganizationId() {
        return returnDocOrganizationId;
    }

    public void setReturnDocOrganizationId(Integer returnDocOrganizationId) {
        this.returnDocOrganizationId = returnDocOrganizationId;
    }

    public String getReturnDocOrganizationName() {
        return returnDocOrganizationName;
    }

    public void setReturnDocOrganizationName(String returnDocOrganizationName) {
        this.returnDocOrganizationName = returnDocOrganizationName;
    }

    public Integer getControlledByUserId() {
        return controlledByUserId;
    }

    public void setControlledByUserId(Integer controlledByUserId) {
        this.controlledByUserId = controlledByUserId;
    }

    public String getControlledByUserName() {
        return controlledByUserName;
    }

    public void setControlledByUserName(String controlledByUserName) {
        this.controlledByUserName = controlledByUserName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(String registeredAt) {
        this.registeredAt = registeredAt;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public Boolean getReady() {
        return isReady;
    }

    public void setReady(Boolean ready) {
        isReady = ready;
    }

    public Integer getDocumentsCount() {
        return documentsCount;
    }

    public void setDocumentsCount(Integer documentsCount) {
        this.documentsCount = documentsCount;
    }

    public Integer getReadyDocumentsCount() {
        return readyDocumentsCount;
    }

    public void setReadyDocumentsCount(Integer readyDocumentsCount) {
        this.readyDocumentsCount = readyDocumentsCount;
    }

    public Integer getCompletedDocumentsCount() {
        return completedDocumentsCount;
    }

    public void setCompletedDocumentsCount(Integer completedDocumentsCount) {
        this.completedDocumentsCount = completedDocumentsCount;
    }

    public Integer getReturnedDocumentsCount() {
        return returnedDocumentsCount;
    }

    public void setReturnedDocumentsCount(Integer returnedDocumentsCount) {
        this.returnedDocumentsCount = returnedDocumentsCount;
    }
}
