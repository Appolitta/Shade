package stories.model.shademodel.core.model.jobmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Created by wizard on 24.06.2017.
 */
public class JobBase {
    @JsonProperty ("id")
    private int id;

    @JsonProperty("name")
    private String name;

//    @JsonProperty("userId")
//    private int userId;

    @JsonProperty("salary")
    private int salary;

    @JsonProperty("salaryType")
    private Integer salaryType;

    @JsonProperty("categoryId")
    private int categoryId;

    @JsonProperty("isSaved")
    private int isSaved;


    public JobBase() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return name;
    }

    public void setTitle(String Name) {
        this.name = Name;
    }

//    public Integer getUserId() {
//        return userId;
//    }

//    public void setUserId(Integer PosterId) {
//        this.userId = userId;
//    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer Salary) {
    this.salary = Salary;
    }

    public Integer getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(Integer SalaryType) {
        this.salaryType = SalaryType;
    }

    public Integer getCategory() {
        return categoryId;
    }

    public void setCategory(Integer category) {
        this.categoryId = category;
    }

}
