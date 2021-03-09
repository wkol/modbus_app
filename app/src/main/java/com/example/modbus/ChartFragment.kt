package com.example.modbus

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.modbus.databinding.FragmentChartBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

class ChartFragment : Fragment() {
    private lateinit var binding: FragmentChartBinding
    private  lateinit var appContext: Context
    private lateinit var chartMain: LineChart
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chart, container, false)
        appContext = requireActivity().applicationContext
        chartMain = binding.mainChart
        getReadings()
        return binding.root
    }

    private fun getReadings() {
        val request = ServiceBuilder.buildService(ModbusEndpoints::class.java)
        val call = request.getReadings()
        call.enqueue(object : Callback<List<Reading>> {
            override fun onResponse(call: Call<List<Reading>>, response: Response<List<Reading>>) {
                if (response.isSuccessful && response.body() != null) {
                    setupChart(response!!.body() as List<Reading>)
                }
            }
            override fun onFailure(call: Call<List<Reading>>, t: Throwable) {
                Toast.makeText(appContext, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun convertDateToFloat(date: String, firstDate: String): Float {
        val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val dateTimestamp = (formatter.parse(date) as Date).time - (formatter.parse(firstDate) as Date).time
        return dateTimestamp.toFloat()
    }

    private fun setupChart(readings: List<Reading>) {
        val data: MutableList<Entry> = emptyList<Entry>().toMutableList()
        val first = readings[0].date
        val formatter = AxisDateFormatter(first)
        chartMain.xAxis.valueFormatter = formatter
        chartMain.xAxis.labelRotationAngle = 24F
        chartMain.xAxis.position = XAxis.XAxisPosition.BOTTOM
        for (item in readings) {
            data.add(Entry(convertDateToFloat(item.date, first), item.current_l1.toFloat()))
        }
        val dataSet = LineDataSet(data, "Natężenie")
        val lineData = LineData(dataSet)
        chartMain.data = lineData
        chartMain.invalidate()
    }
}