package com.example.weatherapp.viewmodel.main


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.Model
import com.example.weatherapp.repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    val liveData = MutableLiveData<Model>()
    val errorMsg = MutableLiveData<String>()

    fun getData() {
        val request = repository.getRepositoryData()
        request.enqueue(object : Callback<Model> {
            override fun onResponse(call: Call<Model>, response: Response<Model>) {
                if (response.code() == 200) {
                    liveData.postValue(response.body())
                } else {
                    errorMsg.postValue("Error network ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Model>, t: Throwable) {
                errorMsg.postValue(t.message)
            }


        })
    }
}