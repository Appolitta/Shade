package stories.model.shademodel.core.model.jobmodel;

/**
 * Created by wizard on 24.06.2017.
 */
import com.fasterxml.jackson.annotation.JsonProperty;

public enum SalaryType {


    @JsonProperty ("0")
    SALARY_TYPE_0 (0, "Mix", "Mix"),

    @JsonProperty ("1")
    SALARY_TYPE_1 (1, "Fixed", "Fixed");

    private final int salaryType;
    private final String salaryTypeDescription;
    private final String salaryTypeDBName;

    SalaryType(final int salaryType, final String salaryTypeDescription, final String salaryTypeDBName) {
        this.salaryType = salaryType;
        this.salaryTypeDescription = salaryTypeDescription;
        this.salaryTypeDBName = salaryTypeDBName;
    }

    public static SalaryType getById(int id) {
        for(SalaryType salaryType : values()) {
            if(salaryType.salaryType == id) return salaryType;
        }
        return null;
    }

    public int getSalaryTypeId() {
        return salaryType;
    }

    public String getSalaryTypeDescription() {
        return salaryTypeDescription;
    }

    public String getSalaryTypeDBName() {
        return salaryTypeDBName;
    }
}
