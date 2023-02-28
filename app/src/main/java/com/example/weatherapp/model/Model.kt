package com.example.weatherapp

import com.google.gson.annotations.SerializedName

data class Model(
    val weather: List<Weather>,
    val main: Main,
    val sys: Sys,
    val name: String,
)

data class Weather(
    val main: String,
)

data class Main(
    val temp: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
)

data class Sys(
    val country: String,
)