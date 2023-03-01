package com.example.weatherapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.repository.MainRepository
import com.example.weatherapp.services.Api
import com.example.weatherapp.viewmodel.main.MainViewModel
import com.example.weatherapp.viewmodel.main.MainViewModelFactory
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: MainViewModel
    lateinit var lon: String
    lateinit var lat: String

    private val api = Api.getInstance()

    val dialogLoading = DialogLoading(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        window.statusBarColor = Color.parseColor("#F1E0C5")

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(MainRepository(api))
        )[MainViewModel::class.java]
        dialogLoading.DialogLoadingInit()
    }

    override fun onStart() {
        super.onStart()
        observers()
    }

    override fun onResume() {
        super.onResume()
        getLocation()
    }

    private fun observers() {
        viewModel.wheaterData.observe(this, Observer {
            if (it.isSuccessful) {
                val response = it.body()
                if (response != null) {
                    val main = response.weather[0].main
                    setForm(response)
                    setBackground(main)
                }
            }
            dialogLoading.DialogLoadingFinish()
        })

        viewModel.errorMsg.observe(this, Observer {
            dialogLoading.DialogLoadingFinish()
            Snackbar.make(
                binding.root,
                "Location not found",
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction("OK") {
                }
                .setActionTextColor(Color.parseColor("#FFFFFF"))
                .show()
        })
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        val location = LocationServices.getFusedLocationProviderClient(this)

        location.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                lon = location.longitude.toString()
                lat = location.latitude.toString()
                viewModel.getData(lat, lon)
            }
        }.addOnFailureListener {
            dialogLoading.DialogLoadingFinish()
            Snackbar.make(
                binding.root,
                "Please turn on your location",
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction("OK") {
                }
                .setActionTextColor(Color.parseColor("#FFFFFF"))
                .show()
        }

    }

    private fun setForm(
        form: Model,
    ) {
        val celsius = resources.getString(R.string.celsius)

        binding.apply {
            txtTemp.text = form.main.temp.toInt().toString().plus(celsius)
            txtMinTemp.text = form.main.tempMin.toInt().toString().plus(celsius)
            txtMaxTemp.text = form.main.tempMax.toInt().toString().plus(celsius)
            txtLocal.text = form.name.plus(", ").plus(form.sys.country)
            txtCurrentWeather.text = form.weather[0].main
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


