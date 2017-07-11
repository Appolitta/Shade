package stories.model.shademodel.core.workflow;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SavePackageFlowModel {
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

    // Nullable.
    @JsonProperty("organizationId")
    private Integer organizationId;

    // Nullable.
    @JsonProperty("returnDocOrganizationId")
    private Integer returnDocOrganizationId;

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

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Integer getReturnDocOrganizationId() {
        return returnDocOrganizationId;
    }

    public void setReturnDocOrganizationId(Integer returnDocOrganizationId) {
        this.returnDocOrganizationId = returnDocOrganizationId;
    }
}
