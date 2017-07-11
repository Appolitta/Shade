package stories.model.shademodel.db;

import java.time.LocalDateTime;

public class UsersDBModel {

    private Integer Id;
    private String Type;
    private String Email;
    private String HashedPassword;
    private String CreatedDateTime;
    private String FbUid;
    private Boolean IsDeleted;
    private String VerificationOptions;
    private String LinkedinUid;
    private String ReviewsSum;
    private String ReviewsCount;
    private String AvatarFileName;
    private String FirstName;
    private String LastName;
    private String TiedUserId;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public Boolean getIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        this.IsDeleted = deleted;
    }

    public String getHashedPassword() {
        return HashedPassword;
    }

    public void setHashedPassword(String password) {
        this.HashedPassword = HashedPassword;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        this.LastName = LastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = Email;
    }

    public String getFbUid() {
        return FbUid;
    }

    public void setFbUid(String FbUid) {
        this.FbUid = FbUid;
    }

    public String getVerificationOption() {
        return VerificationOptions;
    }

    public void setVerificationOption(String VerificationOptions) {
        this.VerificationOptions = VerificationOptions;
    }

    public String getLinkedinUid() {
        return LinkedinUid;
    }

    public void setLinkedinUid(String LinkedinUid) {
        this.LinkedinUid = LinkedinUid;
    }

    public String getReviewsSum() {
        return ReviewsSum;
    }

    public void setReviewsSum(String ReviewsSum) {
        this.ReviewsSum = ReviewsSum;
    }

    public String getReviewsCount() {
        return ReviewsCount;
    }

    public void setReviewsCounte(String ReviewsCount) {
        this.ReviewsCount = ReviewsCount;
    }

    public String getAvatarFileName() {
        return AvatarFileName;
    }

    public void setAvatarFileName(String AvatarFileName) {
        this.AvatarFileName = AvatarFileName;
    }

    public String getCreatedDateTime() {
        return CreatedDateTime;
    }

    public void setCreatedDateTime(String CreatedDateTime) {
        this.CreatedDateTime = CreatedDateTime;
    }

    public String getTiedUserId() {
        return TiedUserId;
    }

    public void setTiedUserId(String TiedUserId) {
        this.TiedUserId = TiedUserId;
    }

}

