package stories.model.shademodel.core.model.jobmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wizard on 04.07.2017.
 */
public class JobFeedModelResponse extends JobModel  {


    /*    @JsonProperty("id")
        private int id;
*/
        @JsonProperty("name")
        private String name;

        @JsonProperty("categoryId")
        private int categoryId;

        @JsonProperty("logoUrl")
        private LogoUrl logoUrl;

        @JsonProperty("isSaved")
        private boolean isSaved;

        @JsonProperty("location")
        private Location location;

        @JsonProperty("poster")
        private Poster poster;


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

   /*     public int getId() {
            return id;
        }
*/
  /*      public void setId(int id) {
            this.id = id;
        }
*/
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

        public String getName() {
            return name;
        }

        public  void setName(String name) {
            this.name = name;
        }

}