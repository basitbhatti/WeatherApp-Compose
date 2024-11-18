package com.shoppingapp.weatherapp_jetpackcompose.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoppingapp.weatherapp_jetpackcompose.api.RetrofitInstance
import com.shoppingapp.weatherapp_jetpackcompose.utils.Constants.API_KEY
import com.shoppingapp.weatherapp_jetpackcompose.utils.NetworkResponse
import kotlinx.coroutines.launch

open class WeatherViewModel : ViewModel() {

    val retrofitInstance = RetrofitInstance.weatherApi

    private val _weatherResult = MutableLiveData<NetworkResponse<Weather>>()
    val weatherResult: LiveData<NetworkResponse<Weather>> = _weatherResult

    fun getData(city: String) {
        _weatherResult.value = NetworkResponse.Loading

        viewModelScope.launch {
            try {
                val response = retrofitInstance.getWeather(API_KEY, city)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                        val url = "https:${it.current.condition.icon}"
                        Log.d("TAGICON", url)
                    }
                } else {
                    _weatherResult.value = NetworkResponse.Error("Error Occurred!")
                }
            } catch (e : Exception){
                _weatherResult.value = NetworkResponse.Error("Error Occurred!")
            }
        }
    }


}