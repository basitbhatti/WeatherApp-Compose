package com.shoppingapp.weatherapp_jetpackcompose.api

import com.shoppingapp.weatherapp_jetpackcompose.mvvm.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    //https://api.weatherapi.com/v1/current.json?key=aaafc01859e04bdb8ab73150241611&q=London&aqi=no

    @GET("/v1/current.json")
    suspend fun getWeather(
        @Query("key") key : String,
        @Query("q") location : String
    ) : Response<Weather>

}