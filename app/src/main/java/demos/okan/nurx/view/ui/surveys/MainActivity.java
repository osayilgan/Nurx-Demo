package demos.okan.nurx.view.ui.surveys;

import android.os.Bundle;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import demos.okan.nurx.R;
import demos.okan.nurx.base.BaseActivity;
import demos.okan.nurx.service.model.SurveyScreen;
import demos.okan.nurx.service.repository.SurveyRepository;
import demos.okan.nurx.view.ui.surveys.choices.ChoiceFragment;
import demos.okan.nurx.view.ui.surveys.input.InputFragment;
import demos.okan.nurx.view.ui.surveys.rating.RatingFragment;
import demos.okan.nurx.view.ui.surveys.welcome.WelcomeFragment;
import demos.okan.nurx.viewmodel.SurveyViewModel;

public class MainActivity extends BaseActivity implements WelcomeFragment.Callbacks, ISurveyNavigation {

    public static final String FRAGMENT_BUNDLE_DATA_KEY = "FRAGMENT_BUNDLE_DATA_KEY";

    private SurveyViewModel surveyViewModel;
    private int currentSurveyIndex = 0;
    private List<SurveyScreen> surveys;

    private WelcomeFragment welcomeFragment;
    private static final int fragmentContainerId = R.id.fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Attach Root View */
        welcomeFragment = new WelcomeFragment();
        attachRootFragment(welcomeFragment, fragmentContainerId, true);
    }

    @Override
    public void onBackPressed() {

        /* Decrease the Current Survey Index */
        if (currentSurveyIndex > 0) currentSurveyIndex--;

        /* This will Load the Previous Fragment */
        super.onBackPressed();
    }

    /**
     * Loads Survey JSON from API.
     */
    private void loadSurveys(SurveyRepository.SURVEY surveyType) {

        // TODO - Show Loading Dialog

        surveyViewModel.getSurveys(surveyType).observe(this, surveyScreens -> {

            /* Once the Survey is fetched Create and Show Fragments. */
            surveys = surveyScreens;
            createFragmentFromSurveyData(surveys.get(currentSurveyIndex));
        });
    }

    private void createFragmentFromSurveyData(SurveyScreen survey) {

        /* Create Input Fragment if the Data Type is input */
        if (survey.getData().getType().equalsIgnoreCase("input")) createInputFragment(survey);

        /* Create Rating Fragment */
        if (survey.getData().getType().equalsIgnoreCase("rating")) createRatingFragment(survey);

        /* Create Choices Fragment */
        if (survey.getData().getType().equalsIgnoreCase("choices")) createChoicesFragment(survey);
    }

    /**
     * Creates Choices Fragment and Adds it to the BackStack.
     *
     * @param surveyScreen
     */
    private void createChoicesFragment(SurveyScreen surveyScreen) {

        ChoiceFragment choiceFragment = new ChoiceFragment();

        Bundle surveyBundle = new Bundle();
        surveyBundle.putSerializable(FRAGMENT_BUNDLE_DATA_KEY, surveyScreen.getData());

        choiceFragment.setArguments(surveyBundle);

        attachFragment(choiceFragment, fragmentContainerId, true, true);
    }

    /**
     * Creates Fragment for Input Types.
     *
     * @param surveyScreen
     */
    private void createInputFragment(SurveyScreen surveyScreen) {

        InputFragment inputFragment = new InputFragment();

        Bundle surveyBundle = new Bundle();
        surveyBundle.putSerializable(FRAGMENT_BUNDLE_DATA_KEY, surveyScreen.getData());

        inputFragment.setArguments(surveyBundle);

        attachFragment(inputFragment, fragmentContainerId, true, true);
    }

    /**
     * Creates and Attaches the Rating Fragment.
     */
    private void createRatingFragment(SurveyScreen surveyScreen) {

        RatingFragment ratingFragment = new RatingFragment();

        Bundle surveyBundle = new Bundle();
        surveyBundle.putSerializable(FRAGMENT_BUNDLE_DATA_KEY, surveyScreen.getData());

        ratingFragment.setArguments(surveyBundle);

        attachFragment(ratingFragment, fragmentContainerId, true, true);
    }

    /**
     * Called when any of the Survey buttons is clicked.
     *
     * @param surveyType
     */
    @Override
    public void onStartSurveyClicked(SurveyRepository.SURVEY surveyType) {

        /* init View Model from given Survey Type */
        surveyViewModel = ViewModelProviders.of(this).get(SurveyViewModel.class);

        /* Load Surveys */
        loadSurveys(surveyType);
    }

    @Override
    public void onClickNextSurvey() {

        /* Increase the Current Survey Index */
        currentSurveyIndex++;

        /* check if it's the End of the Survey */
        if (currentSurveyIndex >= surveys.size()) {

            /* Reset Index */
            currentSurveyIndex = 0;

            /* Message */
            Toast.makeText(this, "End of Survey !", Toast.LENGTH_LONG).show();

            /* End Of Survey. Go Home View */
            popFragmentStack();

        } else {

            /* Load Next Survey */
            createFragmentFromSurveyData(surveys.get(currentSurveyIndex));
        }
    }
}
