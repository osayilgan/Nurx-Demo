package demos.okan.nurx.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import demos.okan.nurx.service.model.SurveyScreen;
import demos.okan.nurx.service.repository.SurveyRepository;

public class SurveyViewModel extends AndroidViewModel {

    private SurveyRepository repositoryInstance;

    public SurveyViewModel(@NonNull Application application) {
        super(application);
        repositoryInstance = SurveyRepository.getInstance();
    }

    public LiveData<List<SurveyScreen>> getSurveys(SurveyRepository.SURVEY surveyType) {
        return repositoryInstance.loadSurveys(surveyType);
    }
}
