package com.example.weatherapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.repository.MainRepository
import com.example.weatherapp.services.Api
import com.example.weatherapp.viewmodel.main.MainViewModel
import com.example.weatherapp.viewmodel.main.MainViewModelFactory
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import java.util.*

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

        window.statusBarColor = Color.parseColor("#C9B79C")

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
                    val ic = response.weather[0].icon
                    setForm(response)
                    setBackground(ic)
                }
            }
            dialogLoading.DialogLoadingFinish()
        })

        viewModel.errorMsg.observe(this, Observer {
            dialogLoading.DialogLoadingFinish()
            Snackbar.make(
                binding.root,
                it,
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
            txtMaxTemp.text = form.main.tempMax.toInt().toString().plus("/")
            txtLocal.text = form.name.plus(", ").plus(form.sys.country)

            txtFeelsLike.text = form.main.feelsLike.toInt().toString().plus(celsius)
            txtHumidity.text = form.main.humidity.toString().plus(" %")
            txtWind.text = form.wind.speed.toString().plus(" m/s")
        }
    }

    private fun setBackground(ic: String) {

        val icCurrent = binding.icCurrentWheater
        val txtCurrent = binding.txtCurrentWeather
        val imgBg = binding.imgBg
        val txtWhite = getColor(R.color.white)

        fun setTxtWhite() {
            binding.apply {
                txtTemp.setTextColor(txtWhite)
                txtLocal.setTextColor(txtWhite)
                txtCurrentWeather.setTextColor(txtWhite)
                txtMinTemp.setTextColor(txtWhite)
                txtMaxTemp.setTextColor(txtWhite)
            }
        }
        when (ic) {
            "01n" -> {
                txtCurrent.text = getString(R.string.clear_sky)
                Glide.with(this).asGif().load(R.drawable.nigth).into(imgBg)
                icCurrent.setAnimation(R.raw.clear_night)
                icCurrent.playAnimation()
                setTxtWhite()
            }
            "02n", "03n", "04n" -> {
                txtCurrent.text = getString(R.string.cloudy)
                Glide.with(this).asGif().load(R.drawable.nigth).into(imgBg)
                icCurrent.setAnimation(R.raw.cloud_night)
                icCurrent.playAnimation()
                setTxtWhite()
            }
            "09n", "10n" -> {
                txtCurrent.text = getString(R.string.rain)
                Glide.with(this).asGif().load(R.drawable.nigth).into(imgBg)
                icCurrent.setAnimation(R.raw.rain_night)
                icCurrent.playAnimation()
                setTxtWhite()
            }
            "13n" -> {
                txtCurrent.text = getString(R.string.snow)
                Glide.with(this).asGif().load(R.drawable.nigth).into(imgBg)
                icCurrent.setAnimation(R.raw.snow_night)
                icCurrent.playAnimation()
                setTxtWhite()
            }
            "50n" -> {
                txtCurrent.text = getString(R.string.mist)
                Glide.with(this).asGif().load(R.drawable.nigth).into(imgBg)
                icCurrent.setAnimation(R.raw.mist)
                icCurrent.playAnimation()
                setTxtWhite()
            }
            "01d" -> {
                txtCurrent.text = getString(R.string.clear_sky)
                icCurrent.setAnimation(R.raw.clear_sky)
                icCurrent.playAnimation()
                Glide.with(this).asGif().load(R.drawable.sunny).into(imgBg)

            }
            "02d" -> {
                txtCurrent.text = getString(R.string.few_clouds)
                icCurrent.setAnimation(R.raw.few_clouds)
                icCurrent.playAnimation()
                Glide.with(this).load(R.drawable.cloud).into(imgBg)
            }
            "03d" -> {
                txtCurrent.text = getString(R.string.scatterd_clouds)
                icCurrent.setAnimation(R.raw.scattered_clouds)
                icCurrent.playAnimation()
                Glide.with(this).load(R.drawable.cloud).into(imgBg)
            }
            "04d" -> {
                txtCurrent.text = getString(R.string.broken_clouds)
                icCurrent.setAnimation(R.raw.broken_clouds)
                icCurrent.playAnimation()
                Glide.with(this).load(R.drawable.cloud).into(imgBg)
            }
            "09d" -> {
                txtCurrent.text = getString(R.string.shower_rain)
                icCurrent.setAnimation(R.raw.shower_rain)
                icCurrent.playAnimation()
                Glide.with(this).asGif().load(R.drawable.rain).into(imgBg)
                setTxtWhite()
            }
            "10d" -> {
                txtCurrent.text = getString(R.string.rain)
                icCurrent.setAnimation(R.raw.rain)
                icCurrent.playAnimation()
                Glide.with(this).asGif().load(R.drawable.rain).into(imgBg)
                setTxtWhite()
            }
            "11d", "11n" -> {
                txtCurrent.text = getString(R.string.thunderstorm)
                icCurrent.setAnimation(R.raw.thunderstorm)
                icCurrent.playAnimation()
                Glide.with(this).load(R.drawable.thunderstorm).into(imgBg)
                setTxtWhite()
            }
            "13d" -> {
                txtCurrent.text = getString(R.string.snow)
                icCurrent.setAnimation(R.raw.snow)
                icCurrent.playAnimation()
                Glide.with(this).asGif().load(R.drawable.snow).into(imgBg)
            }
            "50d" -> {
                txtCurrent.text = getString(R.string.mist)
                icCurrent.setAnimation(R.raw.mist)
                icCurrent.playAnimation()
                Glide.with(this).load(R.drawable.cloud).into(imgBg)
            }
            else -> {
                txtCurrent.text = getString(R.string.clear_sky)
                icCurrent.setAnimation(R.raw.clear_sky)
                icCurrent.playAnimation()
                Glide.with(this).load(R.drawable.cloud).into(imgBg)
            }
        }
    }
}


