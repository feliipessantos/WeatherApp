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
            txtCurrentWeather.text = form.weather[0].main
        }
    }

    private fun setBackground(currentTxt: String) {
        val icCurrent = binding.icCurrentWheater
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
        when (currentTxt) {
            "Clear" -> {
                //  icCurrent.setImageResource(R.drawable.ic_sun)
                Glide.with(this).asGif().load(R.drawable.sunny).into(imgBg)
            }
            "Snow" -> {
                // icCurrent.setImageResource(R.drawable.ic_sun)
                Glide.with(this).asGif().load(R.drawable.snow).into(imgBg)
            }
            "Rain" -> {
                //  icCurrent.setImageResource(R.drawable.ic_rain)
                setTxtWhite()
                Glide.with(this).asGif().load(R.drawable.rain).into(imgBg)
            }
            "Thunderstorm" -> {
                //   icCurrent.setImageResource(R.drawable.ic_thunderstorm)
                Glide.with(this).load(R.drawable.thunderstorm).into(imgBg)
                setTxtWhite()
            }
            else -> {
                //  icCurrent.setImageResource(R.drawable.ic_cloudy)
                icCurrent.setAnimation(R.raw.few_clouds)
                icCurrent.playAnimation()
                Glide.with(this).load(R.drawable.cloud).into(imgBg)
            }
        }
    }
}


