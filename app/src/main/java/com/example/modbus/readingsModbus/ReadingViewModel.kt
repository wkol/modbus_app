package com.example.modbus.readingsModbus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modbus.networkUtilities.ModbusEndpoints
import com.example.modbus.networkUtilities.Reading
import com.example.modbus.networkUtilities.ServiceBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReadingViewModel : ViewModel() {

    // Property storing the current reading
    private val _data: MutableLiveData<MutableList<ReadingItem>> =
        MutableLiveData(Reading("2020-01-01T12:00:00").getCategoriesList().toMutableList())
    val data: LiveData<MutableList<ReadingItem>>
        get() = _data

    private val _date: MutableLiveData<String> = MutableLiveData("Loading...")
    val date: LiveData<String>
        get() = _date

    init {
        getData()
    }

    fun updateData() {
        viewModelScope.launch {
            while (true) {
                getData()
                delay(10000L)
            }
        }
    }

    private fun getData() {
        val request = ServiceBuilder.buildService(ModbusEndpoints::class.java)
        val call = request.getLastReading()
        call.enqueue(object : Callback<Reading> {
            override fun onResponse(call: Call<Reading>, response: Response<Reading>) {
                if (response.isSuccessful && response.body() != null) {
                    _date.value = response.body()!!.getDateString()
                    _data.value = response.body()!!.getCategoriesList().toMutableList()
                }
            }

            override fun onFailure(call: Call<Reading>, t: Throwable) {
                _date.value = t.toString()
            }
        })
    }
}
