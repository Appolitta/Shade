package stories.model.shademodel.core.model.usermodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import stories.model.shademodel.core.model.accountmodel.UserModel;
import stories.model.shademodel.core.model.jobmodel.Location;
import stories.model.shademodel.core.model.accountmodel.ShadeUserModelResponse;
/**
 * Created by wizard on 14.07.2017.
 */
public class UserAboutMeResponse extends UserModel {

    private String firstName;
    private String lastName;
    private String email;
    private String aboutMe;
    private String website;
    private Location location;

    @JsonProperty("access_token")
    private String access_token;

    @JsonProperty("shadeAboutMe")
    private ShadeUserModelResponse shadeUserAboutMeModelResponse;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public ShadeUserModelResponse getShadeUserAboutMeModelResponse() {
        return shadeUserAboutMeModelResponse;
    }

    public void setShadeUserAboutMeModelResponse(ShadeUserModelResponse shadeUserAboutMeModelResponse) {
        this.shadeUserAboutMeModelResponse = shadeUserAboutMeModelResponse;
    }
}