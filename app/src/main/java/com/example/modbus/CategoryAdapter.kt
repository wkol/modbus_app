package com.example.modbus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.modbus.databinding.CatergoryRowLayoutBinding
import com.example.modbus.databinding.ReadingRowLayoutBinding

class CategoryAdapter(private val categoriesVisible: MutableList<Any>) :
    RecyclerView.Adapter<ViewHolder>() {

    inner class CategoryViewHolder(val binding: CatergoryRowLayoutBinding) :
        ViewHolder(binding.root) {
        fun bind(data: Category) {
            binding.textView.text = data.name
            binding.executePendingBindings()
        }
    }

    inner class ReadingViewHolder(private val binding: ReadingRowLayoutBinding) :
        ViewHolder(binding.root) {
        fun bind(data: ValueElement) {
            binding.valueElement = data
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.catergory_row_layout -> {
                val binding = CatergoryRowLayoutBinding.inflate(inflater)
                CategoryViewHolder(binding)
            }
            R.layout.reading_row_layout -> {
                val binding = ReadingRowLayoutBinding.inflate(inflater)
                ReadingViewHolder(binding)
            }
            else -> throw Exception("Invalid viewType")
        }
    }
    override fun getItemViewType(position: Int): Int {
        if (categoriesVisible[position] is Category) {
            return R.layout.catergory_row_layout
        } else if (categoriesVisible[position] is ValueElement) {
            return R.layout.reading_row_layout
        }
        return -1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val row = categoriesVisible[position]
        when (row is Category) {
            true -> {
                (holder as CategoryViewHolder).bind(row)
                holder.binding.imgArrow.setOnClickListener {
                    when (row.isExpanded) {
                        true -> collapseRow(position)
                        else -> expandRow(position)
                    }
                }
                holder.binding.textView.setOnClickListener {
                    when (row.isExpanded) {
                        true -> collapseRow(position)
                        else -> expandRow(position)
                    }
                }
            }
            false -> (holder as ReadingViewHolder).bind(row as ValueElement)
        }
    }

    private fun expandRow(position: Int) {
        val row = categoriesVisible[position]
        val nextPosition = position + 1
        if (row is Category) {
            categoriesVisible.addAll(nextPosition, row.elements)
            (categoriesVisible[position] as Category).isExpanded = true
            notifyItemRangeInserted(position, row.elements.size)
        }
        notifyItemRangeChanged(nextPosition, itemCount)
    }

    private fun collapseRow(position: Int) {
        val row = categoriesVisible[position]
        val nextPosition = position + 1
        if (row is Category) {
            for (element in row.elements) {
                categoriesVisible.removeAt(nextPosition)
                notifyItemRemoved(nextPosition)
            }
            (categoriesVisible[position] as Category).isExpanded = false
        }
        notifyItemRangeChanged(nextPosition, itemCount)
    }

    fun updateData(newData: List<Any>) {
        var indexC = -1
        var indexV = 0
        for (item in categoriesVisible) {
            if (item is Category) {
                indexC++
                indexV = 0
                item.elements = (newData[indexC] as Category).elements
            } else {
                categoriesVisible[categoriesVisible.indexOf(item)] = (newData[indexC] as Category).elements[indexV]
                indexV++
            }
        }
        notifyItemRangeChanged(0, itemCount)
    }
    override fun getItemCount(): Int = categoriesVisible.size
}
