package demos.okan.nurx.view.ui.surveys.input;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import demos.okan.nurx.R;
import demos.okan.nurx.service.model.Data;
import demos.okan.nurx.service.model.Field;
import demos.okan.nurx.util.Utils;
import demos.okan.nurx.view.ui.surveys.ISurveyNavigation;

import static demos.okan.nurx.view.ui.surveys.MainActivity.FRAGMENT_BUNDLE_DATA_KEY;

public class InputFragment extends Fragment implements View.OnClickListener {

    private Data inputData;
    private List<Input> generatedInputs;

    private ImageButton nextSurvey;

    ISurveyNavigation surveyNavigationCallback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Get Extras from Arguments */
        inputData = (Data) getArguments().getSerializable(FRAGMENT_BUNDLE_DATA_KEY);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            surveyNavigationCallback = (ISurveyNavigation) context;
        } catch (ClassCastException exception) {
            throw new ClassCastException("Parent Activity must implement Callbacks from " + this.getClass().getSimpleName());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View parent = inflater.inflate(R.layout.input_fragment_layout, container, false);

        nextSurvey = parent.findViewById(R.id.nextSurvey);

        /* Set Click Listeners */
        nextSurvey.setOnClickListener(this);

        /* Generate Input Fields */
        List<Field> fields = inputData.getFields();

        if (fields != null) {

            LinearLayout inputHolder = parent.findViewById(R.id.inputHolder);

            generatedInputs = new ArrayList<>();

            for (Field field : fields) {

                EditText mInput = inflateInputView(field);

                Input input = new Input();
                input.setInput(mInput);
                input.setName(field.getName());
                input.setOptional(isFieldOptional(field));

                generatedInputs.add(input);

                /* Add to UI */
                inputHolder.addView(mInput);
            }
        }

        // TODO - Read Cached Data.

        return parent;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (nextSurvey.getId() == id) {
            onClickNext();
        }
    }

    /**
     * Validate input before navigating to Next Survey Screen.
     */
    private void onClickNext() {

        for (Input input : generatedInputs) {

            boolean isEmpty = Utils.isNullOrEmpty(input.getInput());

            if ( ! input.isOptional() && isEmpty) {
                Toast.makeText(getContext(),input.getName() + " is not optional field", Toast.LENGTH_LONG).show();
                return;
            }
        }

        /* If the Validation is provided then Call Parent Activity's Next method. */
        surveyNavigationCallback.onClickNextSurvey();
    }

    /**
     * Adds Input EditText to the main layout.
     *
     * @param field
     */
    private EditText inflateInputView(Field field) {

        EditText input = (EditText) getLayoutInflater().inflate(R.layout.edit_text, null, false);
        input.setHint(field.getName());
        input.setInputType(getInputType(field.getType()));

        return input;
    }

    /**
     * Retrieves the EditText input type from given String.
     *
     * @param inputTypeString
     * @return
     */
    private int getInputType(String inputTypeString) {

        int defaultType = InputType.TYPE_CLASS_TEXT;

        if (inputTypeString != null && inputTypeString.equals("string")) return InputType.TYPE_CLASS_TEXT;
        else if (inputTypeString != null && inputTypeString.equals("number")) return InputType.TYPE_CLASS_NUMBER;
        else return defaultType;
    }

    /**
     * Checks whether the given Field is optional.
     *
     * @param field
     * @return
     */
    private boolean isFieldOptional(Field field) {

        if (inputData.getOptionalFields() == null) return false;
        return inputData.getOptionalFields().contains(field.getName());
    }
}
