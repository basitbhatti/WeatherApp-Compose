package com.shoppingapp.weatherapp_jetpackcompose.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.pdftoexcel.bankstatementconverter.utils.PrefManager
import com.shoppingapp.weatherapp_jetpackcompose.mvvm.Weather
import com.shoppingapp.weatherapp_jetpackcompose.mvvm.WeatherViewModel
import com.shoppingapp.weatherapp_jetpackcompose.utils.Constants.IS_CITY_SET
import com.shoppingapp.weatherapp_jetpackcompose.utils.Constants.LOCATION
import com.shoppingapp.weatherapp_jetpackcompose.utils.NetworkResponse

@Composable
fun WeatherScreen(
    context : Context,
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel
) {

    var weatherResult = viewModel.weatherResult.observeAsState()

    val keyboardController = LocalSoftwareKeyboardController.current

    var city by remember {
        mutableStateOf("")
    }

//    if (PrefManager(context).getBoolean(IS_CITY_SET)){
//        city = PrefManager(context).getString(LOCATION)
//        viewModel.getData(city)
//    }

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
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Gray,
                        focusedLabelColor = Color.Gray
                    ),
                    textStyle = TextStyle(
                        color = Color.Black
                    ),
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
                        keyboardController?.hide()
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
                    PrefManager(context).saveBoolean(IS_CITY_SET, true)
                    PrefManager(context).saveString(LOCATION, result.data.location.name)
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

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(text = "${data.current.temp_c}°C", fontSize = 32.sp, color = Color.Black)
        }

//        val url = "https:${data.current.condition.icon}"
//        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
//            AsyncImage(
//                modifier = Modifier.size(128.dp),
//                model = url,
//                contentDescription = data.current.condition.text
//            )
//        }
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(text = "${data.current.condition.text}", fontSize = 22.sp, color = Color.Black)
        }

        Card (
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            shape = RoundedCornerShape(10.dp)
        ) {

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {

                    Column(modifier = Modifier.fillMaxHeight().weight(0.5f),
                         horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center){
                        Text(text = data.current.humidity, fontWeight = FontWeight.Medium, fontSize = 16.sp, color = Color.Black)
                        Text(text = "Humidity", fontSize = 12.sp, color = Color.Black)
                    }

                    Column(modifier = Modifier.fillMaxHeight().weight(0.5f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center){
                        Text(text = "${data.current.wind_kph} km/h",fontWeight = FontWeight.Medium, fontSize = 16.sp, color = Color.Black)
                        Text(text = "Wind Speed", fontSize = 12.sp, color = Color.Black)
                    }

                }

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {

                    Column(modifier = Modifier.fillMaxHeight().weight(0.5f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center){
                        Text(text = data.current.uv,fontWeight = FontWeight.Medium, fontSize = 16.sp, color = Color.Black)
                        Text(text = "UV", fontSize = 12.sp, color = Color.Black)
                    }

                    Column(modifier = Modifier.fillMaxHeight().weight(0.5f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center){
                        Text(text = "${data.current.precip_mm} mm",fontWeight = FontWeight.Medium, fontSize = 16.sp, color = Color.Black)
                        Text(text = "Precipitation", fontSize = 12.sp, color = Color.Black)
                    }

                }

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {

                    Column(modifier = Modifier.fillMaxHeight().weight(0.5f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center){
                        Text(text = data.location.localtime.split(" ")[1],fontWeight = FontWeight.Medium, fontSize = 16.sp, color = Color.Black)
                        Text(text = "Local Time", fontSize = 12.sp, color = Color.Black)
                    }

                    Column(modifier = Modifier.fillMaxHeight().weight(0.5f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center){
                        Text(text = data.location.localtime.split(" ")[0], fontWeight = FontWeight.Medium, fontSize = 16.sp, color = Color.Black)
                        Text(text = "Local Date", fontSize = 12.sp, color = Color.Black)
                    }

                }


            }

        }

    }

}


@Preview
@Composable
private fun Preview() {
    val context = LocalContext.current
    val fakeViewModel = WeatherViewModel().apply {
    }
    WeatherScreen(viewModel = fakeViewModel, context = LocalContext.current)
}