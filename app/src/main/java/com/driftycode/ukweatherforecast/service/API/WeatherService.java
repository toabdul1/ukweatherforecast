package com.driftycode.ukweatherforecast.service.API;

import com.driftycode.ukweatherforecast.service.models.CitiesWeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Abdul Raheem on 16/11/17.
 */
public interface WeatherService {

    /* @Method: getWeatherForecaseForUK
     * Return : Forecase data based on the city and country name
     */
    @GET("/data/2.5/forecast")
    Call<CitiesWeatherModel> getWeatherForecaseForUK(@Query("q") String cityName, @Query("mode") String responseFormat, @Query("cnt") String number, @Query("appid") String appId);


}
