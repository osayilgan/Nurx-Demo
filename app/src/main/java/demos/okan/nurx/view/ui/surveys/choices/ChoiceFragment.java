package demos.okan.nurx.view.ui.surveys.choices;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import demos.okan.nurx.R;
import demos.okan.nurx.service.model.Choice;
import demos.okan.nurx.service.model.Data;
import demos.okan.nurx.view.ui.surveys.ISurveyNavigation;

import static demos.okan.nurx.view.ui.surveys.MainActivity.FRAGMENT_BUNDLE_DATA_KEY;

public class ChoiceFragment extends Fragment implements View.OnClickListener {

    private Data data;
    private List<ChoiceInput> generatedInputs;

    private ISurveyNavigation surveyNavigationCallback;

    private ImageButton nextSurvey;
    private TextView maxChoices;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Get Extras from Arguments */
        data = (Data) getArguments().getSerializable(FRAGMENT_BUNDLE_DATA_KEY);
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

        View parent = inflater.inflate(R.layout.choice_fragment, container, false);

        /* Init UI and Set Listeners */
        nextSurvey = parent.findViewById(R.id.nextSurvey);
        nextSurvey.setOnClickListener(this);

        maxChoices = parent.findViewById(R.id.max_choices_text);
        maxChoices.setText("Max Choices : " + data.getMaxChoices());

        /* Generate Input Fields */
        List<Choice> choices = data.getChoices();

        if (choices != null) {

            LinearLayout choiceHolder = parent.findViewById(R.id.choiceHolder);

            generatedInputs = new ArrayList<>();

            for (Choice choice: choices) {

                CheckBox mInput = inflateInputView(choice);
                mInput.setOnClickListener(this);

                ChoiceInput input = new ChoiceInput();
                input.setCheckBox(mInput);
                input.setName(choice.getName());
                input.setValue(choice.getValue());

                generatedInputs.add(input);

                /* Add to UI */
                choiceHolder.addView(mInput);
            }
        }

        return parent;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (nextSurvey.getId() == id) {
            onClickNextSurvey();
        } else if (v instanceof CheckBox) {

            /* check if the CheckBox is Clicked */
            toggleCheckBox((CheckBox) v);
        }
    }

    /**
     * Iterates through generated Input and Toggles the checkBox
     * @param checkBox
     */
    private void toggleCheckBox(CheckBox checkBox) {

        for (ChoiceInput input : generatedInputs) {

            if (input.getCheckBox().getId() == checkBox.getId()) {
                input.getCheckBox().setChecked(checkBox.isChecked());
                return;
            }
        }
    }

    /**
     * Called when the Next Button is Clicked.
     */
    private void onClickNextSurvey() {

        /* Check if Max Choice Condition is met */

        /* Selection Number cannot be '0' */
        /* Selection Number cannot be more than 'Max_Choices' */

        int numberOfSelection = 0;
        for (ChoiceInput input : generatedInputs) {
            if (input.getCheckBox().isChecked()) numberOfSelection++;
        }

        if (numberOfSelection == 0) {
            Toast.makeText(getContext(), "Selection Cannot be 0", Toast.LENGTH_SHORT).show();
            return;
        } else if (numberOfSelection > data.getMaxChoices()) {
            Toast.makeText(getContext(), "Selection Cannot be more than " + data.getMaxChoices(), Toast.LENGTH_SHORT).show();
            return;
        }

        /* Send Callback to Parent Activity */
        surveyNavigationCallback.onClickNextSurvey();
    }

    /**
     * Adds Input CheckBox to the main layout.
     *
     * @param choice
     */
    private CheckBox inflateInputView(Choice choice) {

        CheckBox input = (CheckBox) getLayoutInflater().inflate(R.layout.choice_fragment_input, null, false);
        input.setText(choice.getName());
        input.setId(ViewCompat.generateViewId());

        return input;
    }
}
