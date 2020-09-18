package com.example.basicweatherapp.Retrofit;

import android.util.JsonReader;

import com.google.gson.JsonObject;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface WeatherApi {

    @Headers({
            "x-rapidapi-host: community-open-weather-map.p.rapidapi.com",
            //"x-rapidapi-key: YOUR API KEY"
    })

    @GET("weather")
    Call<JsonObject> getCurrentWeather(
            @Query("units") String Units,
            @Query("q") String City
    );

    @Headers({
            "x-rapidapi-host: community-open-weather-map.p.rapidapi.com",
            //"x-rapidapi-key: YOUR API KEY"
    })
    @GET("forecast")
    Call<JsonObject> getForecastWeather(
            @Query("units") String Units,
            @Query("q") String City
    );
}
