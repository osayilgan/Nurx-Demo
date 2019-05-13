package demos.okan.nurx.view.ui.surveys.input;

import android.widget.EditText;

/**
 * Wrapper Class to Hold Input UI Elements and Validation Data.
 */
public class Input {

    private EditText input;
    private String name;
    private boolean isOptional;

    public Input() {}

    public Input(EditText input, String name, boolean isOptional) {
        this.input = input;
        this.name = name;
        this.isOptional = isOptional;
    }

    public EditText getInput() {
        return input;
    }

    public void setInput(EditText input) {
        this.input = input;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOptional() {
        return isOptional;
    }

    public void setOptional(boolean optional) {
        isOptional = optional;
    }
}
