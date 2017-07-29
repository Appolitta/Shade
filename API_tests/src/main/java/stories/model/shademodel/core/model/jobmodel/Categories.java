package stories.model.shademodel.core.model.jobmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Created by wizard on 24.06.2017.
 */
public enum Categories {


    @JsonProperty ("0")
    CATEGORY_NO_CATEGORY (0, "no category", ""),

    @JsonProperty ("1")
    CATEGORY_1 (1, "Business & Finance", "Business & Finance"),

    @JsonProperty ("2")
    CATEGORY_2 (2, "Media & Tech", "Media & Tech"),

    @JsonProperty ("3")
    CATEGORY_3 (3, "Education", "Education"),

    @JsonProperty ("4")
    CATEGORY_4 (4, "Services", "Services"),

    @JsonProperty ("5")
    CATEGORY_5 (5, "Home", "Home"),

    @JsonProperty ("6")
    CATEGORY_6 (6, "Delivery & Shopping", "Delivery & Shopping");

    private final int categoryId;
    private final String categoryDescription;
    private final String categoryDBName;

    Categories(final int categoryId, final String categoryDescription, final String categoryDBName) {
        this.categoryId = categoryId;
        this.categoryDescription = categoryDescription;
        this.categoryDBName = categoryDBName;
    }

    public static Categories getById(int id) {
        for(Categories category : values()) {
            if(category.categoryId == id) return category;
        }
        return null;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public String getCategoryDBName() {
        return categoryDBName;
    }
}

