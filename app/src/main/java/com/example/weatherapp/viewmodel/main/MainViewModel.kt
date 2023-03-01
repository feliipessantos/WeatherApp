package com.example.weatherapp.viewmodel.main

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.Model
import com.example.weatherapp.repository.MainRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    val _wheaterData = MutableLiveData<Response<Model>>()
    val wheaterData: LiveData<Response<Model>> = _wheaterData

    val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> = _errorMsg

    @SuppressLint("MissingPermission")
    fun getData(lat: String, lon: String) {
        viewModelScope.launch {
            val request = repository.getRepositoryData(lat, lon)
            if (request.code() == 200) {
                _wheaterData.postValue(request)
            }
        }

    }

}