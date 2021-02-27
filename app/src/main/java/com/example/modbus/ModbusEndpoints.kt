package com.example.modbus

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ModbusEndpoints {
    @GET("/readings_last/")
    fun getLastReading(): Call<Reading>
}