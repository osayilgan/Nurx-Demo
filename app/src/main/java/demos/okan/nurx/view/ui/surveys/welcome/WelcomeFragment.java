package demos.okan.nurx.view.ui.surveys.welcome;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import demos.okan.nurx.R;
import demos.okan.nurx.service.repository.SurveyRepository;

public class WelcomeFragment extends Fragment implements View.OnClickListener {

    private Callbacks callbacks;

    /* UI Elements */
    private Button survey1Button;
    private Button survey2Button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.welcome_fragment, container, false);

        survey1Button = parent.findViewById(R.id.button_survey_1);
        survey2Button = parent.findViewById(R.id.button_survey_2);

        survey1Button.setOnClickListener(this);
        survey2Button.setOnClickListener(this);

        return parent;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callbacks = (Callbacks) context;
        } catch (ClassCastException exception) {
            throw new ClassCastException("Parent Activity must implement Callbacks from " + this.getClass().getSimpleName());
        }
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (survey1Button.getId() == id) {
            callbacks.onStartSurveyClicked(SurveyRepository.SURVEY.SURVEY_1);
        } else if (survey2Button.getId() == id) {
            callbacks.onStartSurveyClicked(SurveyRepository.SURVEY.SURVEY_2);
        }
    }

    public interface Callbacks {

        /**
         * Called when Start Survey is Clicked.
         *
         * @param surveyType
         */
        void onStartSurveyClicked(SurveyRepository.SURVEY surveyType);
    }
}
