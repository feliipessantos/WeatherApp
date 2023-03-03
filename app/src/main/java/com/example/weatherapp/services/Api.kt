package com.example.weatherapp.services

import com.example.weatherapp.model.Model
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("weather")
    suspend fun getLocalData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String = "e15ff8279f7c587e99d8ca51b0493623",
        @Query("units") units: String = "metric",
    ): Response<Model>

    @GET("weather")
    suspend fun getSearchData(
        @Query("q") city: String,
        @Query("appid") appid: String = "e15ff8279f7c587e99d8ca51b0493623",
        @Query("units") units: String = "metric",
    ): Response<Model>

    companion object {
        private val api: Api by lazy {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .build()

            retrofit.create(Api::class.java)
        }

        fun getInstance(): Api {
            return api
        }
    }

}

