package com.example.weatherapp.services

import com.example.weatherapp.Model
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("data/2.5/weather")
    fun getData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String,
        @Query("units") units: String,
    ): Call<Model>


    companion object {
        private val api: Api by lazy {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.openweathermap.org/")
                .build()


            retrofit.create(Api::class.java)
        }

        fun getInstance(): Api {
            return api
        }
    }

}

