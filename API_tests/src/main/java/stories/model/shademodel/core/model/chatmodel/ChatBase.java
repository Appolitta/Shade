package stories.model.shademodel.core.model.chatmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import stories.model.shademodel.core.model.accountmodel.UserModel;

/**
 * Created by weezlabs on 4/4/17.
 */
public class ChatBase {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("status")
    private int status;

    @JsonProperty("isChatRead")
    private boolean isRead;

    @JsonProperty("isOpponentReported")
    private boolean isOpReported;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isOpReported() {
        return isOpReported;
    }

    public void setOpReported(boolean opReported) {
        isOpReported = opReported;
    }


}
