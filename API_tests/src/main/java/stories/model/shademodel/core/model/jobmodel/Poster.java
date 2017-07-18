package stories.model.shademodel.core.model.jobmodel;


/**
 * Created by wizard on 12.07.2017.
 */
public class Poster {

    private int id;
    private String firstName;
    private String lastName;
    public RatingInfo ratingInfo;

    public RatingInfo getRaitingInfo() {
        return ratingInfo;
    }

    public void setRaitingInfo(RatingInfo raitingInfo) {
        this.ratingInfo = raitingInfo;
    }

    public String getLastName() {

        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




}
