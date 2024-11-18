package com.shoppingapp.weatherapp_jetpackcompose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoppingapp.weatherapp_jetpackcompose.mvvm.Weather
import com.shoppingapp.weatherapp_jetpackcompose.mvvm.WeatherViewModel
import com.shoppingapp.weatherapp_jetpackcompose.utils.NetworkResponse

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel
) {

    var weatherResult = viewModel.weatherResult.observeAsState()

    var city by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Row(
            modifier = modifier
                .height(90.dp)
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .weight(0.8f),
                contentAlignment = Alignment.Center
            ) {

                OutlinedTextField(
                    modifier = Modifier.padding(bottom = 10.dp),
                    value = city,
                    onValueChange = { city = it },
                    label = {
                        Text(text = "Search for any location")
                    }
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .weight(0.2f),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        viewModel.getData(city)
                    },
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search Icon"
                    )
                }
            }
        }
        when (val result = weatherResult.value) {
            is NetworkResponse.Error -> {
                Box(
                    modifier =
                    modifier.fillMaxSize()
                ) {
                    Text(text = result.message)
                }
            }

            NetworkResponse.Loading -> {
                Box(
                    modifier =
                    modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is NetworkResponse.Success -> {
                Box(
                    modifier =
                    modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    WeatherDetails(data = result.data)
                }
            }

            null -> {}
        }
    }
}


@Composable
fun WeatherDetails(
    modifier: Modifier = Modifier,
    data: Weather
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 15.dp),
            verticalAlignment = Alignment.Bottom
        ) {

            Icon (
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = data.location.name
            )

            Spacer(modifier = modifier.width(10.dp))

            Text(text = data.location.name, fontSize = 22.sp, color = Color.Black)

            Spacer(modifier = modifier.width(10.dp))

            Text(text = data.location.country, fontSize = 16.sp, color = Color.Black)

        }

        Text(text = data.location.name, fontSize = 22.sp, color = Color.Black)



    }

}


@Preview
@Composable
private fun Preview() {
    val context = LocalContext.current
    val fakeViewModel = WeatherViewModel().apply {
    }

    WeatherScreen(viewModel = fakeViewModel)
}