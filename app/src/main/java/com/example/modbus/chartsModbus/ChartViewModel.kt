package com.example.modbus.chartsModbus

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.modbus.ChartReading
import com.example.modbus.convertDateToFloat
import com.example.modbus.networkUtilities.ModbusEndpoints
import com.example.modbus.networkUtilities.ServiceBuilder
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.utils.EntryXComparator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.Collections

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

val API_VALUES = listOf(
    "total_power",
    "total_reactive_power",
    "total_apparent_power",
    "power_l1",
    "power_l2",
    "power_l3",
    "reactive_power_l1",
    "reactive_power_l2",
    "reactive_power_l3",
    "voltage_13",
    "voltage_12",
    "voltage_23",
    "voltage_l1",
    "voltage_l2",
    "voltage_l3",
    "current_l1",
    "current_l2",
    "current_l3",
    "current_n",
    "frequency",
    "total_cos",
    "cos_l1",
    "cos_l2",
    "cos_l3",
    "input_ea",
    "return_ea",
    "ind_eq",
    "cap_eq",
    "power_dc",
    "voltage_dc",
    "current_dc",
    "power_inv",
    "reactive_power_inv",
    "apparent_power_inv",
    "voltage_uab_inv",
    "voltage_ubc_inv",
    "voltage_uca_inv",
    "voltage_ua_inv",
    "voltage_ub_inv",
    "voltage_uc_inv",
    "current_a_inv",
    "current_b_inv",
    "current_c_inv",
    "current_avg_inv",
    "frequency_inv",
    "cos_inv",
    "heat_sink_temp_inv",
    "energy"
)

class ChartViewModel : ViewModel(), DateListener {

    // Property storing selected chart
    private var _chartSelection: Int = 0
    val chartSelection: Int
        get() = _chartSelection

    // Property storing chart values
    private val _chartData = MutableLiveData(emptyList<Entry>().toMutableList())
    val chartData: MutableLiveData<MutableList<Entry>>
        get() = _chartData

    // Property storing chart's period starting date
    private val _dateStart: MutableLiveData<String> = MutableLiveData("Start")
    val dateStart: LiveData<String>
        get() = _dateStart

    // Property storing chart's period ending date
    private val _dateEnd: MutableLiveData<String> = MutableLiveData("Koniec")
    val dateEnd: LiveData<String>
        get() = _dateEnd

    // Property storing chart's loading state
    private val _loading: MutableLiveData<Int> = MutableLiveData(View.GONE)
    val loading: LiveData<Int>
        get() = _loading

    override fun onDatePositiveSelected(date: String, tag: String) {
        if (tag == "startDate") {
            _dateStart.value = date
        } else {
            _dateEnd.value = date
        }
        changeChartSelection(_chartSelection)
    }

    // Method allowing to get and parse  the chart data from api
    private fun getChartFromApi() {
        val request = ServiceBuilder.buildService(ModbusEndpoints::class.java)
        val call = request.getChartReadings(
            API_VALUES[_chartSelection],
            _dateStart.value!!,
            _dateEnd.value!!
        )
        call.enqueue(object : Callback<List<ChartReading>> {
            override fun onResponse(
                call: Call<List<ChartReading>>,
                response: Response<List<ChartReading>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    updateDates(response.body()!!)
                    Timber.i(response.message())
                }
            }

            override fun onFailure(call: Call<List<ChartReading>>, t: Throwable) {
                Timber.e(t)
            }
        })
    }

    // Method allowing to change the selected chart
    fun changeChartSelection(newId: Int) {
        _chartSelection = newId
        if (_dateEnd.value != "Koniec" && _dateStart.value != "Start") {
            _loading.value = View.VISIBLE
            getChartFromApi()
        }
    }

    // Method which updates data to match selected dates
    fun updateDates(data: List<ChartReading>) {
        val newData: MutableList<Entry> = mutableListOf()
        data.forEach { item ->
            newData.add(
                Entry(
                    convertDateToFloat(item.date, _dateStart.value!!),
                    item.value.toFloat()
                )
            )
        }
        Collections.sort(newData, EntryXComparator())
        _chartData.value = newData
        _loading.value = View.GONE
    }
}
