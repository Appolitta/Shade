package stories.model.shademodel.core.model.usermodel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

//The object that returns from server
public class UserModelResponse extends UserProfileBase {

    @JsonProperty("roleIds")
    private List<Integer> roleIds;

    @JsonProperty("roleNames")
    private List<String> roleNames;

    @JsonProperty("shadeUser")
    private ShadeUser shadeUser;

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public List<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(List<String> roleNames) {
        this.roleNames = roleNames;
    }
}
