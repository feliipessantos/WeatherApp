package com.example.weatherapp.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.Model
import com.example.weatherapp.repository.MainRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    private val _weatherData = MutableLiveData<Response<Model>>()
    val weatherData: LiveData<Response<Model>> = _weatherData

    private val _searchData = MutableLiveData<Response<Model>>()
    val searchData: LiveData<Response<Model>> = _searchData

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> = _errorMsg

    fun getLocalData(lat: String, lon: String) {
        viewModelScope.launch {
            val request = repository.getRepositoryLocalData(lat, lon)
            if (request.code() == 200) {
                _weatherData.postValue(request)
            } else {
                _errorMsg.postValue("Location not found")
            }
        }
    }

    fun getSearchData(city: String) {
        viewModelScope.launch {
            val request = repository.getRepositorySearchData(city)
            if (request.code() == 200) {
                _searchData.postValue(request)
            } else {
                _errorMsg.postValue("Location not found")
            }
        }
    }
}