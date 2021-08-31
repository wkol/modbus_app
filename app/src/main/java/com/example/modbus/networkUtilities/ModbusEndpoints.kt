package com.example.modbus.networkUtilities

import com.example.modbus.ChartReading
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ModbusEndpoints {
    // Endpoint returning the last reading from the API
    @GET("/readings_last/")
    fun getLastReading(): Call<Reading>

    // Endpoint returning the data for a chart
    @GET("/readings_chart")
    fun getChartReadings(
        @Query("name") name: String,
        @Query("from_date") dateStart: String,
        @Query("to_date") dateEnd: String
    ): Call<List<ChartReading>>
}
