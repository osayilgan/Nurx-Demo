package demos.okan.nurx.service.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import demos.okan.nurx.service.model.SurveyScreen;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SurveyRepository {

    private final NurxService nurxService;

    public enum SURVEY { SURVEY_1, SURVEY_2 }

    private static SurveyRepository instance;

    public synchronized static SurveyRepository getInstance() {

        if (instance == null) instance = new SurveyRepository();
        return instance;
    }

    private SurveyRepository() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NurxService.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        nurxService = retrofit.create(NurxService.class);
    }

    public LiveData<List<SurveyScreen>> loadSurveys(SURVEY survey) {

        final MutableLiveData<List<SurveyScreen>> surveyData = new MutableLiveData<>();

        Call<List<SurveyScreen>> surveyCall = (survey == SURVEY.SURVEY_1) ? nurxService.getSurvey1() : nurxService.getSurvey2();

        surveyCall.enqueue(new Callback<List<SurveyScreen>>() {

            @Override
            public void onResponse(Call<List<SurveyScreen>> call, Response<List<SurveyScreen>> response) {

                if (response.isSuccessful()) {
                    surveyData.setValue(response.body());

                } else {

                    Log.e("SurveyRepository", "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<SurveyScreen>> call, Throwable t) {

                String errorMessage;
                if (t.getMessage() == null) {
                    errorMessage = "unknown error";
                } else {
                    errorMessage = t.getMessage();
                }

                Log.d("SurveyRepository", "onFailure: " + errorMessage);
            }
        });

        return surveyData;
    }
}
