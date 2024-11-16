package com.shoppingapp.weatherapp_jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shoppingapp.weatherapp_jetpackcompose.ui.mvvm.WeatherViewModel
import com.shoppingapp.weatherapp_jetpackcompose.ui.screens.WeatherScreen
import com.shoppingapp.weatherapp_jetpackcompose.ui.theme.WeatherAppJetpackComposeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppJetpackComposeTheme {
                val viewModel : WeatherViewModel by viewModels<WeatherViewModel>()
                WeatherScreen(viewModel = viewModel)
            }
        }
    }


}