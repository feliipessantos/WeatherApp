package com.example.weatherapp

import retrofit2.Call
import retrofit2.http.GET


interface Api {
    @GET("data/2.5/weather?id=3452925&appid=e15ff8279f7c587e99d8ca51b0493623&units=metric")
    fun getData() : Call<Model>
}

