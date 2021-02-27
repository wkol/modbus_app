package com.example.modbus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.modbus.databinding.ReadingRowLayoutBinding


class ReadingAdapter(private val values: List<ValueElement>) :
        RecyclerView.Adapter<ReadingAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: ReadingRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ValueElement) {
            binding.valueElement = data
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ReadingRowLayoutBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(values[position])

    override fun getItemCount(): Int = values.size
}
