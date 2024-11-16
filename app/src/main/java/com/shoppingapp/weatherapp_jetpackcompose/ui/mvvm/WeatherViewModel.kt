package com.shoppingapp.weatherapp_jetpackcompose.ui.mvvm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoppingapp.weatherapp_jetpackcompose.ui.Constants.API_KEY
import com.shoppingapp.weatherapp_jetpackcompose.ui.api.RetrofitInstance
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    val retrofitInstance = RetrofitInstance.weatherApi

    fun getData(city: String) {
        viewModelScope.launch {
            val response = retrofitInstance.getWeather(API_KEY, city)
            if (response.isSuccessful) {
                Log.d("TAGRESPONSE", "isSuccessful: ${response.body()?.current}")
            } else {
                Log.d("TAGRESPONSE", "onError: ${response.body()}")
            }
        }
    }




}