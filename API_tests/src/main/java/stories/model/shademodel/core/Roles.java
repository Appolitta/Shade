package stories.model.shademodel.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Roles {
    @JsonProperty ("0")
    ROLES_NO_ROLE (0, "no role", ""),

    @JsonProperty ("1")
    ROLES_ADMIN (1, "Admin", "Admin"),

    @JsonProperty ("2")
    ROLES_EMPLOYER (2, "Employer", "Employer"),

    @JsonProperty ("3")
    ROLES_EMPLOYEE (3, "Employee", "Employee");

    private final int roleId;
    private final String roleDescription;
    private final String roleDBName;

    Roles(final int roleId, final String roleDescription, final String roleDBName) {
        this.roleId = roleId;
        this.roleDescription = roleDescription;
        this.roleDBName = roleDBName;
    }

    public static Roles getById(int id) {
        for(Roles role : values()) {
            if(role.roleId == id) return role;
        }
        return null;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public String getRoleDBName() {
        return roleDBName;
    }
}
