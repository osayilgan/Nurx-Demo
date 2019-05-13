package demos.okan.nurx.service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SurveyScreen implements Serializable {

    @SerializedName("name")
    @Expose private String name;

    @SerializedName("data")
    @Expose private Data data;

    /** This looks like only available for 'Choices' SurveyScreen */
    @SerializedName("type")
    @Expose private String optional;

    public SurveyScreen() {}

    public SurveyScreen(String name, Data data) {
        this.name = name;
        this.data = data;
    }

    public SurveyScreen(String name, Data data, String optional) {
        this.name = name;
        this.data = data;
        this.optional = optional;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getOptional() {
        return optional;
    }

    public void setOptional(String optional) {
        this.optional = optional;
    }
}
