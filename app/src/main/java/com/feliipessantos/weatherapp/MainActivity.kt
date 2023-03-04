package com.feliipessantos.weatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.feliipesantos.weatherapp.DialogLoading
import com.feliipesantos.weatherapp.model.Model
import com.feliipesantos.weatherapp.repository.MainRepository
import com.feliipesantos.weatherapp.services.Api
import com.feliipesantos.weatherapp.viewmodel.main.MainViewModel
import com.feliipesantos.weatherapp.viewmodel.main.MainViewModelFactory
import com.feliipessantos.weatherapp.databinding.ActivityMainBinding
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: MainViewModel
    private lateinit var lon: String
    private lateinit var lat: String

    private val api = Api.getInstance()
    private val LOCATION_SERVICE_CODE = 1000

    private val dialogLoading = DialogLoading(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.parseColor("#C9B79C")

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(MainRepository(api))
        )[MainViewModel::class.java]
        dialogLoading.dialogLoadingInit(R.layout.dialog_loading)
       permissions()

    }
    override fun onStart() {
        super.onStart()
        observers()
    }

    override fun onResume() {
        super.onResume()
        getLocation()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getSearchData(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun observers() {
        viewModel.weatherData.observe(this, Observer { weatherData ->
            if (weatherData.isSuccessful) {
                val response = weatherData.body()
                if (response != null) {
                    val ic = response.weather[0].icon
                    setFormLocal(response)
                    setBackground(ic)
                }
            }
            dialogLoading.dialogLoadingFinish()
            showContainerDetails()
        })

        viewModel.searchData.observe(this, Observer { searchData ->
            if (searchData.isSuccessful) {
                val response = searchData.body()
                if (response != null) {
                    setSearchedDialog()
                    setFormSearch(response)
                }
            }
        })

        viewModel.errorMsg.observe(this, Observer {
            Snackbar.make(
                binding.root,
                "",
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction("OK") {
                }
                .setText(getString(R.string.not_found_snackbar))
                .setTextColor(Color.WHITE)
                .setBackgroundTint(Color.RED)
                .show()
        })
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        val location = LocationServices.getFusedLocationProviderClient(this)

        location.lastLocation.addOnSuccessListener { local ->
            if (local != null) {
                lon = local.longitude.toString()
                lat = local.latitude.toString()
                viewModel.getLocalData(lat, lon)
            }
        }.addOnFailureListener {
            dialogLoading.dialogLoadingFinish()
            if (isPermissionGranted()) {
                Snackbar.make(
                    binding.root,
                    "",
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction("OK") {
                    }
                    .setText(getString(R.string.turn_on_location_snackbar))
                    .setTextColor(Color.WHITE)
                    .setBackgroundTint(Color.RED)
                    .show()
            }
        }
    }

   private fun requestLocationPermission() {
        return ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
            ), LOCATION_SERVICE_CODE
        )
    }

    private fun isPermissionGranted(): Boolean {
        viewModel.requestPermissionGranted()
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun permissions() {
        if (!isPermissionGranted())
            requestLocationPermission()
        else viewModel.requestPermissionGranted()
    }

    private fun setFormLocal(form: Model) {
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

    private fun setSearchedDialog() {
        binding.dialogSearch.visibility = VISIBLE

        binding.btClose.setOnClickListener {
            binding.dialogSearch.visibility = GONE
        }

    }

    private fun setFormSearch(form: Model) {
        val celsius = resources.getString(R.string.celsius)
        val searchDialogIc = binding.icSearched

        binding.apply {
            txtSearchedCity.text = form.name
            txtSearchedTemp.text = form.main.temp.toInt().toString().plus(celsius)
            txtSearchedTempMax.text = form.main.tempMax.toInt().toString().plus("/")
            txtSearchedTempMin.text = form.main.tempMin.toInt().toString().plus(celsius)
        }

        when (form.weather[0].icon) {
            "01n" -> {
                binding.txtSearchedCurrent.text = getString(R.string.clear_sky)
                searchDialogIc.setAnimation(R.raw.clear_night)
                searchDialogIc.playAnimation()
            }
            "02n", "03n", "04n" -> {
                binding.txtSearchedCurrent.text = getString(R.string.cloudy)
                searchDialogIc.setAnimation(R.raw.cloud_night)
                searchDialogIc.playAnimation()
            }
            "09n", "10n" -> {
                binding.txtSearchedCurrent.text = getString(R.string.rain)
                searchDialogIc.setAnimation(R.raw.rain_night)
                searchDialogIc.playAnimation()
            }
            "13n" -> {
                binding.txtSearchedCurrent.text = getString(R.string.snow)
                searchDialogIc.setAnimation(R.raw.snow_night)
                searchDialogIc.playAnimation()
            }
            "50n", "50d" -> {
                binding.txtSearchedCurrent.text = getString(R.string.mist)
                searchDialogIc.setAnimation(R.raw.mist)
                searchDialogIc.playAnimation()
            }
            "01d" -> {
                binding.txtSearchedCurrent.text = getString(R.string.clear_sky)
                searchDialogIc.setAnimation(R.raw.clear_sky)
                searchDialogIc.playAnimation()
            }
            "02d" -> {
                binding.txtSearchedCurrent.text = getString(R.string.few_clouds)
                searchDialogIc.setAnimation(R.raw.few_clouds)
                searchDialogIc.playAnimation()
            }
            "03d" -> {
                binding.txtSearchedCurrent.text = getString(R.string.scatterd_clouds)
                searchDialogIc.setAnimation(R.raw.scattered_clouds)
                searchDialogIc.playAnimation()
            }
            "04d" -> {
                binding.txtSearchedCurrent.text = getString(R.string.broken_clouds)
                searchDialogIc.setAnimation(R.raw.broken_clouds)
                searchDialogIc.playAnimation()
            }
            "09d" -> {
                binding.txtSearchedCurrent.text = getString(R.string.shower_rain)
                searchDialogIc.setAnimation(R.raw.shower_rain)
                searchDialogIc.playAnimation()
            }
            "10d" -> {
                binding.txtSearchedCurrent.text = getString(R.string.rain)
                searchDialogIc.setAnimation(R.raw.rain)
                searchDialogIc.playAnimation()
            }
            "11d", "11n" -> {
                binding.txtSearchedCurrent.text = getString(R.string.thunderstorm)
                searchDialogIc.setAnimation(R.raw.thunderstorm)
                searchDialogIc.playAnimation()
            }
            "13d" -> {
                binding.txtSearchedCurrent.text = getString(R.string.snow)
                searchDialogIc.setAnimation(R.raw.snow)
                searchDialogIc.playAnimation()
            }
            else -> {
                binding.txtSearchedCurrent.text = getString(R.string.clear_sky)
                searchDialogIc.setAnimation(R.raw.clear_sky)
                searchDialogIc.playAnimation()
            }
        }

    }

    private fun showContainerDetails(){
        binding.containerDetails.visibility = VISIBLE
    }
}


