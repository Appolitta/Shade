package stories.model.shademodel.core.model.jobmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by wizard on 12.07.2017.
 */
public class LogoUrl {

    private String urlToFolder;
    private String fileName;

    public String getUrlToFolder() {
        return urlToFolder;
    }

    public void setUrlToFolder(String urlToFolder) {
        this.urlToFolder = urlToFolder;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
