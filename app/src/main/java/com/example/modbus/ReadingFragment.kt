package com.example.modbus

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modbus.databinding.FragmentReadingBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ReadingFragment : Fragment() {
    private lateinit var binding: FragmentReadingBinding
    private val scope = MainScope()
    private var job: Job? = null
    private lateinit var viewModel: ReadingViewModel
    private lateinit var appContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        appContext = requireContext()
        viewModel = ViewModelProvider(this).get(ReadingViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reading, container, false)
        binding.categoryRecycle.adapter = CategoryAdapter(viewModel.data.value ?: mutableListOf())
        binding.categoryRecycle.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        binding.categoryRecycle.setHasFixedSize(true)
        viewModel.data.observe(
            viewLifecycleOwner,
            {
                (binding.categoryRecycle.adapter as CategoryAdapter).updateData(viewModel.data.value ?: mutableListOf())
                binding.dateView.text = viewModel.date.value
                Toast.makeText(appContext, "Dane zaktualizowane", Toast.LENGTH_SHORT).show()
            }
        )
        startUpdates()
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        stopUpdates()
    }

    private fun startUpdates() {
        stopUpdates()
        job = scope.launch {
            while (true) {
                viewModel.updateData() // the function that should be ran every second
                delay(20000)
            }
        }
    }

    private fun stopUpdates() {
        job?.cancel()
        job = null
    }
}
