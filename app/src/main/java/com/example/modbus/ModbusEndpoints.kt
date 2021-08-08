package com.example.modbus

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ModbusEndpoints {
    @GET("/readings_last/")
    fun getLastReading(): Call<Reading>

    @GET("/readings_chart")
    fun getChartReadings(@Query("name") name: String,
                         @Query("from_date") dateStart: String,
                         @Query("to_date") dateEnd: String) : Call<List<ChartReading>>
}
