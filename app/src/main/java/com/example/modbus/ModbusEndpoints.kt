package com.example.modbus

import retrofit2.Call
import retrofit2.http.GET

interface ModbusEndpoints {
    @GET("/readings_last/")
    fun getLastReading(): Call<Reading>

    @GET("/readings_chart/")
    fun getReadings(): Call<List<Reading>>
}
