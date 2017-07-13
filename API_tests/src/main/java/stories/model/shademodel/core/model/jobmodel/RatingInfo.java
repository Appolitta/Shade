package stories.model.shademodel.core.model.jobmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by wizard on 12.07.2017.
 */
public class RatingInfo {

    @JsonProperty ("rating")
    private long rating;

    @JsonProperty ("numberOfReviews")
    private int numberOfReviews;

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(int numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

}