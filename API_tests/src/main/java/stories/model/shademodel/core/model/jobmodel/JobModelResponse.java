package stories.model.shademodel.core.model.jobmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    private SalaryType salaryType;

    @JsonProperty("salaryNames")
    private List<String> salaryNames;

    @JsonProperty("name")
    private String name;

    @JsonProperty("isReported")
    private Boolean isReported;

 /*   @JsonProperty("salary")
    private int salary;
*/
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

   /* public Integer getSalaryType() {
        return salaryType;
    }
*/
    public void setSalaryType(SalaryType salaryType) {
        this.salaryType = salaryType;
    }

    public List<String> getSalaryNames() {
        return salaryNames;
    }

    public void setSalaryNames(List<String> salaryNames) {
        this.salaryNames = salaryNames;
    }

 /*   public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
*/
 /*   public List<String> getPoster() {
        return poster;
    }

    public void setPoster(List<String> poster) {
        this.poster = poster;
    }
*/


    public Boolean getIsReported() {
        return isReported;
    }

    public void setIsIsReported(Boolean isReported) {
        this.isReported = isReported;
    }
}

