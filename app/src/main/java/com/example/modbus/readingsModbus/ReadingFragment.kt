package com.example.modbus.readingsModbus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modbus.R
import com.example.modbus.databinding.FragmentReadingBinding

class ReadingFragment : Fragment() {
    private lateinit var binding: FragmentReadingBinding
    private lateinit var viewModel: ReadingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(ReadingViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reading, container, false)
        binding.readingViewModel = viewModel
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)

        binding.categoryRecycle.adapter = CategoryAdapter(viewModel.data.value ?: mutableListOf())
        binding.categoryRecycle.layoutManager =
            LinearLayoutManager(requireActivity().applicationContext)
        binding.categoryRecycle.setHasFixedSize(true)

        // Observe the last reading data
        viewModel.data.observe(
            viewLifecycleOwner,
            {
                (binding.categoryRecycle.adapter as CategoryAdapter).updateData(
                    viewModel.data.value ?: mutableListOf()
                )
                Toast.makeText(activity, "Dane zaktualizowane", Toast.LENGTH_SHORT).show()
            }
        )
        viewModel.updateData()

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_chart, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
