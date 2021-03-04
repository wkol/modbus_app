package com.example.modbus

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.modbus.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val data: MutableList<Category> = Reading("2020-01-01T12:00:00").createCategories().toMutableList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateData()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.categoryRecycle.adapter = CategoryAdapter(data)
        binding.categoryRecycle.adapter!!.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.categoryRecycle.setHasFixedSize(true)
        binding.categoryRecycle.layoutManager = LinearLayoutManager(this)
        val delay = 20000
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                handler.postDelayed(this, delay.toLong())
                updateData()
            }
        }, delay.toLong())
    }

    private fun updateData() {
        val request = ServiceBuilder.buildService(ModbusEndpoints::class.java)
        val call = request.getLastReading()
        call.enqueue(object : Callback<Reading> {
            override fun onResponse(call: Call<Reading>, response: Response<Reading>) {
                if (response.isSuccessful) {
                    val newData = response.body()!!.createCategories()
                    data.forEachIndexed { index, category -> category.elements = newData[index].elements }
                    binding.categoryRecycle.adapter!!.notifyItemRangeChanged(1, 5)
                    binding.dateView.text = response.body()!!.getDateString()
                    Toast.makeText(this@MainActivity, "Dane zaktualizowane", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<Reading>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}