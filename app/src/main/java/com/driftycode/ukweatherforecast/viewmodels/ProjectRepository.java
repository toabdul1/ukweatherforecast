package com.driftycode.ukweatherforecast.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.driftycode.ukweatherforecast.base.WeatherApplication;
import com.driftycode.ukweatherforecast.service.API.WeatherService;
import com.driftycode.ukweatherforecast.service.models.CitiesWeatherModel;
import com.driftycode.ukweatherforecast.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Abdul Raheem on 16/11/17.
 */

public class ProjectRepository {

    private static final String TAG = "ProjectRepository";
    public static ProjectRepository projectRepository;
    public static WeatherService weatherService;

    public static WeatherService getWeatherInstance() {
        weatherService = WeatherApplication.initRestClient();
        return weatherService;
    }

    public static ProjectRepository getInstance() {
        projectRepository = new ProjectRepository();
        weatherService = WeatherApplication.initRestClient();
        return projectRepository;
    }

    /*
     * Method: getWeatherDataByCityCode
     * It fetches the data from HTTP API based on the country name - convert into observable livedata object
     */
    public LiveData<CitiesWeatherModel> getWeatherDataByCityCode(String cityName) {
        final MutableLiveData<CitiesWeatherModel> data = new MutableLiveData<>();

        weatherService.getWeatherForecaseForUK(cityName, Constants.JSON_FORMAT, Constants.MAXCOUNT_FORMAT, Constants.API_KEY).enqueue(new Callback<CitiesWeatherModel>() {
            @Override
            public void onResponse(@NonNull Call<CitiesWeatherModel> call, @NonNull Response<CitiesWeatherModel> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<CitiesWeatherModel> call, @NonNull Throwable t) {
                Log.d("", "Call Error" + t.getMessage());
            }
        });

        return data;
    }
}
