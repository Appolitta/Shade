package stories.model.shademodel.core.model.chatmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

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
}
