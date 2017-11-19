package com.driftycode.ukweatherforecast.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.driftycode.ukweatherforecast.service.models.CitiesWeatherModel;

/**
 * Created by Abdul Raheem on 19/11/17.
 */

public class ProjectViewModel extends ViewModel {
    private LiveData<CitiesWeatherModel> countryWeatherObservable;

    public ProjectViewModel() {
        countryWeatherObservable = null;
    }

    /*
     * @Method: getWeatherByCountry
     * Returns: Observable Object with API response data
     */
    public LiveData<CitiesWeatherModel> getWeatherByCountry(String countryID) {
        ProjectRepository projectRepository = ProjectRepository.getInstance();
        countryWeatherObservable = projectRepository.getWeatherDataByCityCode(countryID);
        return countryWeatherObservable;
    }
}
