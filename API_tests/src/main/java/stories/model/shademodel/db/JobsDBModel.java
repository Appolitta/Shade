package stories.model.shademodel.db;

import java.time.LocalDateTime;

public class JobsDBModel {

    private Integer Id;
    private String Name;
    private String Summary;
    private Integer Salary;
    private Integer UserId;
    private LocalDateTime CreatedDateTime;
    private LocalDateTime StartDate;
    private Integer Status;
    private String LogoFileName;
    private Integer CategoryId;
    private Boolean IsDeleted;
    private Integer LocationId;
    private String Description;
    private Integer SalaryType;
    private LocalDateTime EndDate;
    private LocalDateTime StartTime;
    private LocalDateTime EndTime;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Boolean getDeleted() {
        return IsDeleted;
    }

    public void setDeleted( boolean isDeleted) {
        this.IsDeleted = isDeleted;
    }

    public String getTitle (){
        return Name;
    }

    public void setTitle(String name){
        this.Name = name;
    }

    public String getSummary (){
        return Summary;
    }

    public void setSummary(String summary){
        this.Summary = summary;
    }

    public Integer getSalary (){
        return Salary;
    }

    public void setSalary(Integer salary){
        this.Salary = salary;
    }

    public Integer getUserId (){
        return UserId;
    }

    public void setPosterId(Integer posterId){
        this.UserId = posterId;
    }

    public Integer getStatus (){
        return Status;
    }

    public void setStatus(Integer status){
        this.Status = status;
    }

    public Integer getCategoryIde (){
        return CategoryId;
    }

    public void setCategoryId(Integer categoryId){
        this.CategoryId = categoryId;
    }

    public Integer getLocationId (){
        return LocationId;
    }

    public void setLocationId(Integer locationId){
        this.LocationId = locationId;
    }

    public String getDescription (){
        return Description;
    }

    public void setDescription(String description){
        this.Description = description;
    }

    public Integer getSalaryType (){
        return SalaryType;
    }

    public void setSalaryType(Integer salaryType){
        this.SalaryType = salaryType;
    }

    public String getLogoFileName (){
        return LogoFileName;
    }

    public void setLogoFileName(String logoFileName){
        this.LogoFileName = logoFileName;
    }

  /*  private LocalDateTime EndDate;
    private LocalDateTime StartTime;
    private LocalDateTime EndTime;
    private LocalDateTime CreatedDateTime;
    private LocalDateTime StartDate;
*/

}