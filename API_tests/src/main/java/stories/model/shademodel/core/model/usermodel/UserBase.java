package stories.model.shademodel.core.model.usermodel;

import com.fasterxml.jackson.annotation.JsonProperty;


public class UserBase {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("isDeleted")
    private Boolean isDeleted;

    // Nullable.
    @JsonProperty("lockedUntil")
    private String lockedUntil;

    @JsonProperty("password")
    private String password;

    @JsonProperty("passwordType")
    private String passwordType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getLockedUntil() {
        return lockedUntil;
    }

    public void setLockedUntil(String lockedUntil) {
        this.lockedUntil = lockedUntil;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordType() {
        return passwordType;
    }

    public void setPasswordType(String passwordType) {
        this.passwordType = passwordType;
    }
}
