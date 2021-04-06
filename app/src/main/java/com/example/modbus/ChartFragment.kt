package com.example.modbus

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.modbus.databinding.FragmentChartBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ChartFragment : Fragment() {
    private lateinit var binding: FragmentChartBinding
    private lateinit var appContext: Context
    private lateinit var chartMain: LineChart
    private lateinit var viewModel: ChartViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(ChartViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chart, container, false)
        appContext = requireActivity().applicationContext
        chartMain = binding.mainChart
        chartMain.description.isEnabled = false
        chartMain.xAxis.labelRotationAngle = 24F
        chartMain.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chartMain.xAxis.labelCount = 3
        ArrayAdapter.createFromResource(
            appContext,
            R.array.charts_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.chartSelection.adapter = adapter
        }

        binding.chartSelection.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.currentChart.value = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        }
        binding.startDateButton.setOnClickListener {
            DatePickerFragment(viewModel).show(childFragmentManager, "startDate")
        }
        binding.endDateButton.setOnClickListener {
            DatePickerFragment(viewModel).show(childFragmentManager, "endDate")
        }
        viewModel.dateStart.observe(
            viewLifecycleOwner,
            {
                binding.startDateButton.text = viewModel.dateStart.value!!.removeRange(10, viewModel.dateStart.value!!.length)
            }
        )
        viewModel.dateEnd.observe(
            viewLifecycleOwner,
            {
                binding.endDateButton.text = viewModel.dateEnd.value!!.removeRange(10, viewModel.dateEnd.value!!.length)
            }
        )
        viewModel.currentChart.observe(
            viewLifecycleOwner,
            {
                viewModel.updateDates()
                chartMain.xAxis.valueFormatter = AxisDateFormatter(viewModel.dateStart.value!!)
                chartMain.data = LineData(LineDataSet(viewModel.data[it], LABELS[viewModel.currentChart.value!!]))
                chartMain.invalidate()
            }
        )
        viewModel.isLoaded.observe(
            viewLifecycleOwner,
            {
                if (it) {
                    binding.startDateButton.isEnabled = true
                    binding.endDateButton.isEnabled = true
                    binding.infoTextChart.visibility = View.GONE
                }
            }
        )
        return binding.root
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.endDateButton.visibility = View.GONE
            binding.startDateButton.visibility = View.GONE
            binding.chartSelection.visibility = View.GONE
            (activity as AppCompatActivity).supportActionBar?.hide()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.endDateButton.visibility = View.VISIBLE
            binding.startDateButton.visibility = View.VISIBLE
            binding.chartSelection.visibility = View.VISIBLE
            (activity as AppCompatActivity).supportActionBar?.show()
        }
    }
}

class DatePickerFragment(private val onDateSelected: DateListener) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth, 0, 0, 0)
        onDateSelected.onDatePositiveSelected(format.format(calendar.time), this.tag!!)
    }
}

interface DateListener {
    fun onDatePositiveSelected(date: String, tag: String)
}
