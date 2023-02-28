package com.example.weatherapp

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.repository.MainRepository
import com.example.weatherapp.services.Api
import com.example.weatherapp.viewmodel.main.LocationViewModel
import com.example.weatherapp.viewmodel.main.LocationViewModelFactory
import com.example.weatherapp.viewmodel.main.MainViewModel
import com.example.weatherapp.viewmodel.main.MainViewModelFactory
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: MainViewModel
    // lateinit var locationViewModel: LocationViewModel

    private val api = Api.getInstance()

    val dialogLoading = DialogLoading(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        window.statusBarColor = Color.parseColor("#F1E0C5")

        viewModel = ViewModelProvider(this, MainViewModelFactory(MainRepository(api)))
            .get(MainViewModel::class.java)
    }
    override fun onStart() {
        super.onStart()
        viewModel.liveData.observe(this, Observer { response ->
            val temp = response.main.temp.toInt().toString()
            val tempMin = response.main.tempMin.toInt().toString()
            val tempMax = response.main.tempMax.toInt().toString()
            val local = response.name
            val country = response.sys.country
            val currentTxt = response.weather.get(0).main

            setForm(temp, tempMin, tempMax, local, currentTxt, country)
            setBackground(currentTxt)
            dialogLoading.DialogLoadingFinish()
        })

        viewModel.errorMsg.observe(this, Observer {
            Snackbar.make(
                binding.root,
                "Please turn on your location",
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction("OK") {
                }
                .setActionTextColor(Color.parseColor("#FFFFFF"))
                .show()
        })
    }
    override fun onResume() {
        super.onResume()
        viewModel.getData()
        dialogLoading.DialogLoadingInit()
    }

    private fun setForm(
        temp: String?,
        tempMin: String?,
        tempMax: String?,
        local: String?,
        currentTxt: String,
        country: String,
    ) {
        binding.apply {
            txtTemp.text = "${temp} ºC"
            txtMinTemp.text = "${tempMin} ºC"
            txtMaxTemp.text = "${tempMax} ºC"
            txtLocal.text = "${local}, ${country}"
            txtCurrentWeather.text = currentTxt
        }
    }
    private fun setBackground(currentTxt: String){
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
    }

}


