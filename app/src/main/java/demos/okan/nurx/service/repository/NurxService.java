package demos.okan.nurx.service.repository;

import java.util.List;

import demos.okan.nurx.service.model.SurveyScreen;
import retrofit2.Call;
import retrofit2.http.GET;

public interface NurxService {

    String BASE_API_URL = "https://s3-us-west-2.amazonaws.com/";

    @GET("/net-nurx-interview/mobile/survey/payload1.json")
    Call<List<SurveyScreen>> getSurvey1();

    @GET("/net-nurx-interview/mobile/survey/payload2.json")
    Call<List<SurveyScreen>> getSurvey2();
}
