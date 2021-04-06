package com.example.modbus

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReadingViewModel : ViewModel() {
    var data: MutableLiveData<MutableList<Any>> = MutableLiveData(Reading("2020-01-01T12:00:00").getCategoriesList().toMutableList())
    val date: MutableLiveData<String> = MutableLiveData()
    init {
        updateData()
    }

    fun updateData() {
        val request = ServiceBuilder.buildService(ModbusEndpoints::class.java)
        val call = request.getLastReading()
        call.enqueue(object : Callback<Reading> {
            override fun onResponse(call: Call<Reading>, response: Response<Reading>) {
                if (response.isSuccessful && response.body() != null) {
                    date.value = response.body()!!.getDateString()
                    data.value = response.body()!!.getCategoriesList().toMutableList()
                }
            }
            override fun onFailure(call: Call<Reading>, t: Throwable) {
                data.value = null
            }
        })
    }
}
