package com.example.weatherapp.repository


import com.example.weatherapp.services.Api

class MainRepository(private val api: Api) {
    val lat = "-29.9545761"
    val lon = "-51.185171"
    val appid: String = "e15ff8279f7c587e99d8ca51b0493623"
    val units: String = "metric"

    fun getRepositoryData() = api.getData(lat, lon, appid, units)

}