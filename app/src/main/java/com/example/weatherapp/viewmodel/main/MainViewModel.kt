package com.example.weatherapp.viewmodel.main

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.Model
import com.example.weatherapp.repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    val _wheaterData = MutableLiveData<Model>()
    val wheaterData: LiveData<Model> = _wheaterData

    val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> = _errorMsg

    @SuppressLint("MissingPermission")
    fun getData(lat: String, lon: String) {
        val request = repository.getRepositoryData(lat, lon)

        request.enqueue(object : Callback<Model> {
            override fun onResponse(call: Call<Model>, response: Response<Model>) {
                if (response.code() == 200) {
                    _wheaterData.postValue(response.body())
                } else {
                    _errorMsg.postValue("Location Not Found")
                }
            }

            override fun onFailure(call: Call<Model>, t: Throwable) {
                _errorMsg.postValue(t.message)
            }

        })
    }
}