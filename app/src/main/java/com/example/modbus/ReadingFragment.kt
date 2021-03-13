package com.example.modbus

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modbus.databinding.FragmentReadingBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReadingFragment : Fragment() {
    private lateinit var binding: FragmentReadingBinding
    private val scope = MainScope()
    private var job: Job? = null
    private var data: MutableList<Any> = Reading("2020-01-01T12:00:00").getCategoriesList().toMutableList()
    private lateinit var appContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        appContext = requireActivity().applicationContext
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reading, container, false)
        binding.categoryRecycle.adapter = CategoryAdapter(data)
        binding.categoryRecycle.setHasFixedSize(true)
        binding.categoryRecycle.layoutManager = LinearLayoutManager(appContext)
        updateData()
        startUpdates()
        return binding.root
    }

    override fun onPause() {
        stopUpdates()
        super.onPause()
    }

    override fun onResume() {
        startUpdates()
        super.onResume()
    }

    override fun onDestroyView() {
        stopUpdates()
        super.onDestroyView()
    }

    private fun updateData() {
        val request = ServiceBuilder.buildService(ModbusEndpoints::class.java)
        val call = request.getLastReading()
        call.enqueue(object : Callback<Reading> {
            override fun onResponse(call: Call<Reading>, response: Response<Reading>) {
                if (response.isSuccessful && response.body() != null) {
                    val newData = response.body()!!.getCategoriesList()
                    (binding.categoryRecycle.adapter as CategoryAdapter).updateData(newData)
                    binding.dateView.text = response.body()!!.getDateString()
                    Toast.makeText(
                        appContext,
                        "Dane zaktualizowane",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

            override fun onFailure(call: Call<Reading>, t: Throwable) {
                Toast.makeText(appContext, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun startUpdates() {
        stopUpdates()
        job = scope.launch {
            while (true) {
                updateData() // the function that should be ran every second
                delay(20000)
            }
        }
    }
    private fun stopUpdates() {
        job?.cancel()
        job = null
    }
}
