package com.example.modbus

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.Entry
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

val LABELS = listOf(
    "Moc czynna z sieci", "Moc bierna z sieci", "Moc pozorna z sieci", "Moc czynna L1",
    "Moc czynna L2", "Moc czynna L3", "Moc bierna  L1", "Moc bierna  L2", "Moc bierna  L3",
    "Napięcie U13", "Napięcie U12", "Napięcie U23", "Napięcie UL1", "Napięcie UL2", "Napięcie UL3",
    "Prąd IL1", "Prąd IL2", "Prąd IL3", "Prąd IN", "Częst.", "Cosinus", "Cos L1", "Cos L2",
    "Cos L3", "Energia czynna Pobrana", "Energia czynna Oddana", "Energia bierna indukcyjna",
    "Energia bierna pojemnościowa", "Moc DC", "Napięcie DC", "Prąd DC",
    "Moc czynna inw.", "Moc bierna inw.", "Moc pozorna inw.", "Napięcie UAB inw.",
    "Napięcie UBC inw.", "Napięcie UCA inw.", "Napięcie UA inw.", "Napięcie UB inw.",
    "Napięcie UC inw.", "Prąd IA inw.", "Prąd IB inw.", "Prąd IC inw.", "Prad średni inw.",
    "Częst. Inwe.", "Cosinus inw.", "Temperatura radiatora", "Energia"

)

class ChartViewModel() : ViewModel(), DateListener {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
    var dateStart: MutableLiveData<String> = MutableLiveData(dateFormat.format(0L))
    var dateEnd: MutableLiveData<String> = MutableLiveData(dateFormat.format(Calendar.getInstance().time))
    private var readings: List<Reading> = emptyList()
    var data: Array<MutableList<Entry>> = Array(48) { mutableListOf() }
    var currentChart: MutableLiveData<Int> = MutableLiveData()
    var isLoaded: MutableLiveData<Boolean> = MutableLiveData(false)
    init {
        getReadings()
    }

    fun getReadings() {
        val request = ServiceBuilder.buildService(ModbusEndpoints::class.java)
        val call = request.getReadings()
        call.enqueue(object : Callback<List<Reading>> {
            override fun onResponse(call: Call<List<Reading>>, response: Response<List<Reading>>) {
                if (response.isSuccessful) {
                    readings = response.body() as List<Reading>
                    dateStart.value = response.body()!![0].date
                    updateDates()
                    isLoaded.value = true
                    currentChart.value = 0
                }
            }
            override fun onFailure(call: Call<List<Reading>>, t: Throwable) {
                readings = emptyList()
            }
        })
    }

    override fun onDatePositiveSelected(date: String, tag: String) {
        if (tag == "startDate") {
            dateStart.value = date
            currentChart.value = currentChart.value!!
        } else {
            dateEnd.value = date
            currentChart.value = currentChart.value!!
        }
    }

    private fun convertDateToFloat(date: String, firstDate: String): Float {
        val dateTimestamp = (dateFormat.parse(date) as Date).time - (dateFormat.parse(firstDate) as Date).time
        return dateTimestamp.toFloat()
    }

    fun updateDates() {
        val newData: MutableList<Entry> = mutableListOf()
        for (item in readings) {
            if (convertDateToFloat(item.date, dateStart.value!!) >= 0 && convertDateToFloat(item.date, dateEnd.value!!) <= 0) {
                newData.add(Entry(convertDateToFloat(item.date, dateStart.value!!), item.getFieldsList()[currentChart.value!!].toFloat()))
            }
        }
        data[currentChart.value!!] = newData
    }
}
