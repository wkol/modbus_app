package com.example.modbus

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
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
import java.util.Calendar

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

        setHasOptionsMenu(true)
        // Get ViewModel
        viewModel = ViewModelProvider(this).get(ChartViewModel::class.java)

        // Set up bindings
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chart, container, false)
        binding.chartViewModel = viewModel
        binding.lifecycleOwner = this
        binding.progressChart.setVisibilityAfterHide(View.GONE)
        // Set up charts options
        chartMain = binding.mainChart
        chartMain.description.isEnabled = false
        chartMain.xAxis.labelRotationAngle = 24F
        chartMain.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chartMain.xAxis.labelCount = 3

        // Create spinner for selecting chart's data
        ArrayAdapter.createFromResource(
            requireActivity().applicationContext,
            R.array.charts_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.chartSelection.adapter = adapter
        }


        binding.chartSelection.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.changeChartSelection(position)
                    binding.progressChart.setVisibilityAfterHide(View.VISIBLE)
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
        viewModel.chartData.observe(
            viewLifecycleOwner, {
                if (it.isNotEmpty()) {
                    chartMain.xAxis.valueFormatter = AxisDateFormatter(viewModel.dateStart.value!!)
                    chartMain.data = LineData(LineDataSet(it!!, LABELS[viewModel.chartSelection]))
                    chartMain.invalidate()
                    Toast.makeText(requireContext(), "Chart ready", Toast.LENGTH_SHORT).show()
                }
            }
        )

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            chartMain.xAxis.textSize = 0.2f
            binding.endDateButton.visibility = View.GONE
            binding.startDateButton.visibility = View.GONE
            binding.chartSelection.visibility = View.GONE
            (activity as AppCompatActivity).supportActionBar?.hide()
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_chart, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}

class DatePickerFragment(private val onDateSelected: DateListener) : DialogFragment(),
    DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth, 0, 0, 0)
        onDateSelected.onDatePositiveSelected(NO_TIME_DATE_FORMAT.format(calendar.time), this.tag!!)
    }
}

interface DateListener {
    fun onDatePositiveSelected(date: String, tag: String)
}
