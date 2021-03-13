package com.example.modbus

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

val LABELS = listOf(
    "Napięcie 13", "Napięcie 12", "Napięcie 23",
    "Natężenie l1", "Natężenie l2", "Natężenie l3",
    "Moc całkowita", "Moc całkowita bierna", "Moc całkowita pozorna",
    "Częstotliwość", "Cos całkowity", "Natężęnie n",
    "Energia pozorna pobrana", "EA pobrana MSB", "Energia pozorna oddana",
    "EA oddana MSB", "Energia bierna indukcyjna", "EQ indukcji MSB",
    "EQ pojemnościowa", "EQ pojemnościowa MSB", "Napięcie l1",
    "Napięcie l2", "Napięcie l3", "Moc l1",
    "Moc l2", "Moc l3", "Moc bierna l1",
    "Moc bierna l2", "Moc bierna l3", "cos l1",
    "cos l2", "cos l3"
    )

class ChartFragment : Fragment(), DateListener {
    private lateinit var binding: FragmentChartBinding
    private lateinit var appContext: Context
    private lateinit var chartMain: LineChart
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    private var dateStart: String = dateFormat.format(0L)
    private var dateEnd: String = dateFormat.format(Calendar.getInstance().time)
    private var readings: List<Reading> = emptyList()
    private var data: Array<MutableList<Entry>> = Array(32) { mutableListOf() }
    private var currentChart: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chart, container, false)
        appContext = requireActivity().applicationContext
        ArrayAdapter.createFromResource(
            appContext,
            R.array.charts_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.chartSelection.adapter = adapter
        }
        chartMain = binding.mainChart
        binding.button.setOnClickListener {
            DatePickerFragment(this).show(childFragmentManager, "startDate")
        }
        binding.button2.setOnClickListener {
            DatePickerFragment(this).show(childFragmentManager, "endDate")
        }
        binding.chartSelection.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                changeChartType(position)
                currentChart = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        }
        getReadings()
        return binding.root
    }

    private fun getReadings() {
        val request = ServiceBuilder.buildService(ModbusEndpoints::class.java)
        val call = request.getReadings()
        call.enqueue(object : Callback<List<Reading>> {
            override fun onResponse(call: Call<List<Reading>>, response: Response<List<Reading>>) {
                if (response.isSuccessful && response.body() != null) {
                    readings = response.body() as List<Reading>
                    setupChart(readings)
                    binding.button.isEnabled = true
                    binding.button2.isEnabled = true
                }
            }
            override fun onFailure(call: Call<List<Reading>>, t: Throwable) {
                Toast.makeText(appContext, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun convertDateToFloat(date: String, firstDate: String): Float {
        val dateTimestamp = (dateFormat.parse(date) as Date).time - (dateFormat.parse(firstDate) as Date).time
        return dateTimestamp.toFloat()
    }

    private fun setupChart(readings: List<Reading>) {
        // val data: Array<MutableList<Entry>> = Array(32) { mutableListOf() }
        val first = readings[0].date
        val formatter = AxisDateFormatter(first)
        chartMain.xAxis.valueFormatter = formatter
        chartMain.xAxis.labelRotationAngle = 24F
        chartMain.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chartMain.xAxis.labelCount = 3

        for (item in readings) {
            val date = convertDateToFloat(item.date, first)
            for ((index, field) in item.getFieldsList().withIndex()) {
                data[index].add(Entry(date, field.toFloat()))
            }
        }
        val dataSet = LineDataSet(data[0], "Natężenie")
        val lineData = LineData(dataSet)
        chartMain.data = lineData
        chartMain.invalidate()
    }

    private fun updateDates(pos: Int) {
        val newData: MutableList<Entry> = mutableListOf()
        for (item in readings) {
            if (convertDateToFloat(item.date, dateStart) > 0 && convertDateToFloat(item.date, dateEnd) < 0) {
                newData.add(Entry(convertDateToFloat(item.date, dateStart), item.getFieldsList()[pos].toFloat()))
            }
        }
        chartMain.data = LineData(LineDataSet(newData, LABELS[pos]))
        chartMain.invalidate()
    }

    private fun changeChartType(type: Int) {
        chartMain.data = LineData(LineDataSet(data[type], LABELS[type]))
        updateDates(currentChart)
        chartMain.invalidate()
    }

    override fun onDatePositiveSelected(date: String, tag: String) {
        if (tag == "startDate") {
            dateStart = date
            binding.button.text = date.removeRange(10, date.length)
        } else {
            dateEnd = date
            binding.button2.text = date.removeRange(10, date.length)
            updateDates(currentChart)
        }
    }
}

class DatePickerFragment(val onDateSelected: DateListener) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth, 0, 0, 0)
        onDateSelected.onDatePositiveSelected(format.format(calendar.time), this.tag!!)
    }
}

interface DateListener {
    fun onDatePositiveSelected(date: String, tag: String)
}