package stories.model.shademodel.core.model.jobmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by wizard on 04.07.2017.
 */
public class JobFeedModelResponse extends JobBase {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Pojo {
        public Pojo() {
        }
        private String success;

        @JsonProperty("id")
        private int id;

        @JsonProperty("categoryId")
        private int categoryId;

        @JsonProperty("isSaved")
        private boolean isSaved;

        @JsonProperty("logoUrl")
        private LogoUrl logoUrl;

        @JsonProperty("location")
        private Location location;

        @JsonProperty("poster")
        private Poster poster;

        @JsonProperty("salaryType")
        private String salaryType;

        @JsonProperty("salaryNames")
        private List<String> salaryNames;

        @JsonProperty("name")
        private String name;

        @JsonProperty("access_token")
        private String access_token;

        @JsonProperty("shadeJob")
        private ShadeJobModelResponse shadeJobModelResponse;

        @JsonProperty("success")
        public String getSuccessPojo() {
            return success;
        }

        public void setSuccessPojo(String success) {
            this.success = success;
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

        public void setLogoUrl(LogoUrl logoUrl) {
            this.logoUrl = logoUrl;
        }

        public LogoUrl getLogoUrl() {
            return logoUrl;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Poster getPoster() {
            return poster;
        }

        public void setPoster(Poster poster) {
            this.poster = poster;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public boolean isSaved() {
            return isSaved;
        }

        public void setSaved(boolean saved) {
            isSaved = saved;
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



        public class LogoUrl {
            public String urlToFolder;
            public String fileName;
        }

        public class Location {
            public int id;
            public String address;
            public long latitude;
            public long longitude;
        }

        public class Poster {
            int id;
            String firstName;
            String lastName;
            ratingInf raitingInf;

        }

        public class ratingInf {
            long rating;
            int numberOfReviews;

        }



    }
}