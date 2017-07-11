package stories.model.shademodel.core.model.jobmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import stories.model.shademodel.core.model.jobmodel.ShadeJobModelResponse;
import stories.model.shademodel.core.model.jobmodel.JobErrorResponse;

import java.util.List;

/**
 * Created by weezlabs on 4/3/17.
 */
public class JobModelResponse extends JobBase {

    @JsonProperty("access_token")
    private String access_token;

    @JsonProperty("shadeJob")
    private ShadeJobModelResponse shadeJobModelResponse;

    @JsonProperty("salaryType")
    private String  salaryType;

    @JsonProperty("salaryNames")
    private List<String> salaryNames;

    @JsonProperty("name")
    private String name;

    @JsonProperty("salary")
    private int salary;

 /*   @JsonProperty("poster")
    private List<String> poster;
*/

    public String getTitle() {
        return name;
    }

    public void setTitle(String access_token) {
        this.name = name;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public ShadeJobModelResponse getShadeJobModelResponse() {
        return shadeJobModelResponse;
    }

    public void setShadeJobModelResponse(ShadeJobModelResponse shadeJobModelResponse) {
        this.shadeJobModelResponse = shadeJobModelResponse;
    }

    public String getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(String salaryType) {
        this.salaryType = salaryType;
    }

    public List<String> getSalaryNames() {
        return salaryNames;
    }

    public void setSalaryNames(List<String> salaryNames) {
        this.salaryNames = salaryNames;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

 /*   public List<String> getPoster() {
        return poster;
    }

    public void setPoster(List<String> poster) {
        this.poster = poster;
    }
*/
}

