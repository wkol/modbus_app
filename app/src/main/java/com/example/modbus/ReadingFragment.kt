package com.example.modbus

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modbus.databinding.FragmentReadingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReadingFragment : Fragment() {
    private lateinit var binding: FragmentReadingBinding
    private var data: MutableList<Any> = Reading("2020-01-01T12:00:00").getCategoriesList().toMutableList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_reading)
        binding.categoryRecycle.adapter = CategoryAdapter(data)
        binding.categoryRecycle.setHasFixedSize(true)
        binding.categoryRecycle.layoutManager = LinearLayoutManager(requireContext())
        updateData()
        val delay = 20000
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                handler.postDelayed(this, delay.toLong())
                updateData()
            }
        }, delay.toLong())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reading, container, false)
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
                    Toast.makeText(requireContext(), "Dane zaktualizowane", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<Reading>, t: Throwable) {
                Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

