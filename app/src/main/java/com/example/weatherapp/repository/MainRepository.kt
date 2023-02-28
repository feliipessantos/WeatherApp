package com.example.weatherapp.repository


import com.example.weatherapp.services.Api

open class MainRepository(private val api: Api) {
    fun getRepositoryData(lat: String, lon: String) = api.getData(lat, lon)
}