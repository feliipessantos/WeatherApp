package com.example.weatherapp

import com.google.gson.annotations.SerializedName

data class Model(
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
    val sys: Sys,
    val name: String,
)

data class Weather(
    val main: String,
    val description: String,
)

data class Main(
    val temp: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    val humidity: Int,
)

data class Wind(
    val speed: Double,
)

data class Sys(
    val country: String,
)