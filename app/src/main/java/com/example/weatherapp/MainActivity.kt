package com.example.weatherapp

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.openweathermap.org/")
        .build()
        .create(Api::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        window.statusBarColor = Color.parseColor("#F1E0C5")

        checkWeather()
    }

    private fun checkWeather() {
        retrofit.getData().enqueue(object : Callback<Model> {
            override fun onFailure(call: Call<Model>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Model>, response: Response<Model>) {
                val temp = response.body()?.main?.temp?.toInt().toString()
                val tempMin = response.body()?.main?.tempMin?.toInt().toString()
                val tempMax = response.body()?.main?.tempMax?.toInt().toString()
                val local = response.body()?.name.toString()
                val current = response.body()?.weather?.get(0)?.main.toString()

                val icCurrent = binding.icCurrent
                val imgBg = binding.imgBg
                when (current) {
                    "Clear" -> {
                        icCurrent.setImageResource(R.drawable.ic_sun)
                        imgBg.setImageResource(R.drawable.sun_img)
                    }
                    "Rain" -> {
                        icCurrent.setImageResource(R.drawable.ic_rain)
                        imgBg.setImageResource(R.drawable.ic_rain)
                    }
                    "Shower rain" -> {
                        icCurrent.setImageResource(R.drawable.ic_rain)
                        imgBg.setImageResource(R.drawable.ic_rain)
                    }
                    "Broken clouds" -> {
                        icCurrent.setImageResource(R.drawable.ic_cloudy)
                        imgBg.setImageResource(R.drawable.broken_clouds_img)
                    }
                    "Thunderstorm" -> {
                        icCurrent.setImageResource(R.drawable.ic_thunderstorm)
                        imgBg.setImageResource(R.drawable.thunderstrom_img)
                    }
                    else -> {
                        icCurrent.setImageResource(R.drawable.ic_cloudy)
                        imgBg.setImageResource(R.drawable.cloud_img)
                    }
                }
                if (temp <= "12") {
                    imgBg.setImageResource(R.drawable.cold_img)
                }
                setForm(temp, tempMin, tempMax, local, current)
            }
        })
    }

    private fun setForm(
        temp: String?,
        tempMin: String?,
        tempMax: String?,
        local: String?,
        current: String,

        ) {

        binding.txtTemp.text = "${temp} ºC"
        binding.txtMinTemp.text = "${tempMin} ºC"
        binding.txtMaxTemp.text = "${tempMax} ºC"
        binding.txtLocal.text = local
        binding.txtCurrent.text = current

    }


}


