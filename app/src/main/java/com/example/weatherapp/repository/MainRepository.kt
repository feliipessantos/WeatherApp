package com.example.weatherapp.repository


import com.example.weatherapp.services.Api

open class MainRepository(private val api: Api) {
    suspend fun getRepositoryLocalData(lat: String, lon: String) = api.getLocalData(lat, lon)
    suspend fun getRepositorySearchData(city: String) = api.getSearchData(city)
}
