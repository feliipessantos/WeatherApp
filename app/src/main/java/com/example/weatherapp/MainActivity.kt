package com.example.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.weatherapp.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val appid = "e15ff8279f7c587e99d8ca51b0493623"
    private val units = "metric"

    val dialogLoading = DialogLoading(this)

    private val retrofit = Retrofit.Builder()
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

        dialogLoading.DialogLoadingInit()
        checkWeather()
    }

    private fun checkWeather() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val lat = location.latitude.toString()
                    val lon = location.longitude.toString()

                    retrofit.getData(lat, lon, appid, units).enqueue(object : Callback<Model> {
                        override fun onFailure(call: Call<Model>, t: Throwable) {
                            Toast.makeText(baseContext, t.message, Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(call: Call<Model>, response: Response<Model>) {
                            val temp = response.body()?.main?.temp?.toInt().toString()
                            val tempMin = response.body()?.main?.tempMin?.toInt().toString()
                            val tempMax = response.body()?.main?.tempMax?.toInt().toString()
                            val local = response.body()?.name.toString()
                            val currentTxt = response.body()?.weather?.get(0)?.main.toString()
                            val country = response.body()?.sys?.country.toString()

                            val icCurrent = binding.icCurrentWheater
                            val imgBg = binding.imgBg
                            when (currentTxt) {
                                "Clear" -> {
                                    icCurrent.setImageResource(R.drawable.ic_sun)
                                    imgBg.setImageResource(R.drawable.sun_img)
                                }
                                "Rain" -> {
                                    icCurrent.setImageResource(R.drawable.ic_rain)
                                    imgBg.setImageResource(R.drawable.rain_img)
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
                            setForm(temp, tempMin, tempMax, local, currentTxt, country)
                            dialogLoading.DialogLoadingFinish()
                        }
                    })
                } else {
                    Snackbar.make(binding.root,
                        "Please turn on your location",
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction("OK") {
                        }
                        .setActionTextColor(Color.parseColor("#FFFFFF"))
                        .show()
                }
            }
    }

    private fun setForm(
        temp: String?,
        tempMin: String?,
        tempMax: String?,
        local: String?,
        currentTxt: String,
        country: String,
    ) {
        binding.txtTemp.text = "${temp} ºC"
        binding.txtMinTemp.text = "${tempMin} ºC"
        binding.txtMaxTemp.text = "${tempMax} ºC"
        binding.txtLocal.text = "${local}, ${country}"
        binding.txtCurrentWeather.text = currentTxt
    }
}


