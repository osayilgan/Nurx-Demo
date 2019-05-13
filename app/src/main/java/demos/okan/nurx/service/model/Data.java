package demos.okan.nurx.service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {

    @SerializedName("type")
    @Expose private String type;


    /** INPUT type Only */

    @SerializedName("fields")
    @Expose private List<Field> fields;

    @SerializedName("optional")
    @Expose private List<String> optionalFields;


    /** RATING type Only */
    @SerializedName("style")
    @Expose private String style;


    /** CHOICES type Only */

    @SerializedName("choices")
    @Expose private List<Choice> choices;

    @SerializedName("max_choices")
    @Expose private int maxChoices;

    public Data() {}

    /**
     * Input Type Constructor
     *
     * @param type
     * @param fields
     * @param optionalFields
     */
    public Data(String type, List<Field> fields, List<String> optionalFields) {
        this.type = type;
        this.fields = fields;
        this.optionalFields = optionalFields;
    }

    /**
     * Rating Type Constructor
     *
     * @param type
     * @param style
     */
    public Data(String type, String style) {
        this.type = type;
        this.style = style;
    }

    /**
     * Choices Type Constructor
     *
     * @param type
     * @param choices
     * @param maxChoices
     */
    public Data(String type, List<Choice> choices, int maxChoices) {
        this.type = type;
        this.choices = choices;
        this.maxChoices = maxChoices;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public List<String> getOptionalFields() {
        return optionalFields;
    }

    public void setOptionalFields(List<String> optionalFields) {
        this.optionalFields = optionalFields;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public int getMaxChoices() {
        return maxChoices;
    }

    public void setMaxChoices(int maxChoices) {
        this.maxChoices = maxChoices;
    }
}
