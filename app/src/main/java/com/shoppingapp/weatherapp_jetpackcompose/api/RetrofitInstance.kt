package com.shoppingapp.weatherapp_jetpackcompose.api

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val BASE_URL = "https://api.weatherapi.com/"

    private var INSTANCE : Retrofit? = null

    fun getInstance() : Retrofit {

        if (INSTANCE == null){
            synchronized(this){
                INSTANCE = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

        }
        return INSTANCE!!
    }

    val weatherApi = getInstance().create(WeatherApi::class.java)




}