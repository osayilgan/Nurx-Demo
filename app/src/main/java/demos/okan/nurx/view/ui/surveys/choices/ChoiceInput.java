package demos.okan.nurx.view.ui.surveys.choices;

import android.widget.CheckBox;

import java.io.Serializable;

public class ChoiceInput implements Serializable {

    private CheckBox checkBox;
    private String name;
    private String value;

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
