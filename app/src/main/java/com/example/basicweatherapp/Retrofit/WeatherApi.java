package com.example.basicweatherapp.Retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface WeatherApi {

    @Headers({
            "x-rapidapi-host: community-open-weather-map.p.rapidapi.com",
            //"x-rapidapi-key: 73d25cc568mshabcdcf3b79ab355p17e08ajsn7c74fb28b9d0" //Token 1
            //"x-rapidapi-key: 487ba7bf52msh3ab44db1157ec0ep1cf508jsn7c8fd2dfcadc" //Token 2
            //"x-rapidapi-key: 5e4943e5ccmsh3568b8a7f8a03c4p19b17ejsn58acb54ce329" //Token 3
    })

    @GET("weather")
    Call<JsonObject> getCurrentWeather(
            @Query("units") String Units,
            @Query("q") String City
    );

    @Headers({
            "x-rapidapi-host: community-open-weather-map.p.rapidapi.com",
            //"x-rapidapi-key: 73d25cc568mshabcdcf3b79ab355p17e08ajsn7c74fb28b9d0" //Token 1
            //"x-rapidapi-key: 487ba7bf52msh3ab44db1157ec0ep1cf508jsn7c8fd2dfcadc" //Token 2
            //"x-rapidapi-key: 5e4943e5ccmsh3568b8a7f8a03c4p19b17ejsn58acb54ce329" //Token 3
    })
    @GET("forecast")
    Call<JsonObject> getForecastWeather(
            @Query("units") String Units,
            @Query("q") String City
    );
}
