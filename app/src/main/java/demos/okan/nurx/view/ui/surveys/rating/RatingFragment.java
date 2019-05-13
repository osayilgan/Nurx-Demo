package demos.okan.nurx.view.ui.surveys.rating;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import demos.okan.nurx.R;
import demos.okan.nurx.service.model.Data;
import demos.okan.nurx.view.ui.surveys.ISurveyNavigation;

import static demos.okan.nurx.view.ui.surveys.MainActivity.FRAGMENT_BUNDLE_DATA_KEY;

public class RatingFragment extends Fragment implements RatingBar.OnRatingBarChangeListener, View.OnClickListener {

    private RatingBar ratingBar;
    private ImageButton nextSurvey;

    private float rating;

    private Data data;

    private ISurveyNavigation surveyNavigationCallback;

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

        View parent = inflater.inflate(R.layout.rating_fragment, container, false);

        /* Init UI and Set Listeners */
        ratingBar = parent.findViewById(R.id.ratingBar);
        ratingBar.setNumStars(getNumberOfStars());
        ratingBar.setOnRatingBarChangeListener(this);

        /* Init UI and Set Listeners */
        nextSurvey = parent.findViewById(R.id.nextSurvey);
        nextSurvey.setOnClickListener(this);

        return parent;
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

        /* Set Rating From User */
        if (fromUser) this.rating = rating;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (nextSurvey.getId() == id) {
            onClickNextSurvey();
        }
    }

    /**
     * Parses Data Style and retrieves the Rating Range.
     *
     * @return
     */
    private int getNumberOfStars() {
        String[] styleArray = data.getStyle().split("to");
        return Integer.parseInt(styleArray[styleArray.length-1]);
    }

    /**
     * Called when Next Survey is Clicked.
     */
    private void onClickNextSurvey() {

        if (rating == 0) {
            Toast.makeText(getContext(), "Rating must be Selected", Toast.LENGTH_SHORT).show();
            return;
        }

        /* Send Callback to Parent Activity */
        surveyNavigationCallback.onClickNextSurvey();
    }
}
