package com.shoppingapp.weatherapp_jetpackcompose

import android.content.Context
import android.net.wifi.hotspot2.pps.HomeSp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shoppingapp.weatherapp_jetpackcompose.mvvm.WeatherViewModel
import com.shoppingapp.weatherapp_jetpackcompose.ui.screens.WeatherScreen
import com.shoppingapp.weatherapp_jetpackcompose.ui.theme.WeatherAppJetpackComposeTheme

class MainActivity : ComponentActivity() {


    lateinit var context : Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppJetpackComposeTheme {
                context = this@MainActivity
                Home()
            }
        }
    }

    @Composable
    fun Home(modifier: Modifier = Modifier) {
        val viewModel: WeatherViewModel by viewModels<WeatherViewModel>()
        WeatherScreen(viewModel = viewModel, context = context)
    }

    @Preview
    @Composable
    private fun HomePrev() {
        Home()
    }

}