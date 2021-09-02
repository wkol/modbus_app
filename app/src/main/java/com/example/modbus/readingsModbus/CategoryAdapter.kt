package com.example.modbus.readingsModbus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.modbus.R
import com.example.modbus.databinding.CatergoryRowLayoutBinding
import com.example.modbus.databinding.ReadingRowLayoutBinding

class CategoryAdapter(private val items: MutableList<ReadingItem>) :
    RecyclerView.Adapter<ViewHolder>() {

    lateinit var clickListener: CategoryClickListener
    class CategoryViewHolder(val binding: CatergoryRowLayoutBinding) :
        ViewHolder(binding.root) {
        fun bind(data: ReadingItem.Category, clickListener: CategoryClickListener) {
            binding.category = data
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CategoryViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = CatergoryRowLayoutBinding.inflate(inflater, parent, false)
                return CategoryViewHolder(binding)
            }
        }
    }

    class ReadingViewHolder(private val binding: ReadingRowLayoutBinding) :
        ViewHolder(binding.root) {
        fun bind(data: ReadingItem.ValueElement) {
            binding.valueElement = data
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ReadingViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ReadingRowLayoutBinding.inflate(inflater, parent, false)
                return ReadingViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            R.layout.catergory_row_layout -> CategoryViewHolder.from(parent)
            R.layout.reading_row_layout -> ReadingViewHolder.from(parent)
            else -> throw ClassCastException("Invalid viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ReadingItem.Category -> R.layout.catergory_row_layout
            is ReadingItem.ValueElement -> R.layout.reading_row_layout
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is CategoryViewHolder -> {
                val row = items[position] as ReadingItem.Category
                holder.bind(row, clickListener)
            }
            is ReadingViewHolder -> {
                val row = items[position] as ReadingItem.ValueElement
                holder.bind(row)
            }
        }
    }

    fun expandRow(category: ReadingItem.Category) {
        val position = items.indexOf(category)
        items.addAll(position + 1, category.elements)
        notifyItemRangeInserted(position + 1, category.elements.size)
        category.isExpanded = true
    }

    fun collapseRow(category: ReadingItem.Category) {
        val position = items.indexOf(category)
        items.subList(position + 1, position + category.elements.size + 1).clear()
        notifyItemRangeRemoved(position + 1, category.elements.size)
        category.isExpanded = false
    }

    fun updateData(newData: List<ReadingItem>) {
        var indexC = -1
        items.forEachIndexed { i, item ->
            if (item is ReadingItem.Category) {
                indexC++
                item.elements = (newData[indexC] as ReadingItem.Category).elements
                if (item.isExpanded) {
                    item.elements.forEachIndexed { idx, element ->
                        items[i + idx + 1] = element
                    }
                }
            }
        }
        notifyItemRangeChanged(0, itemCount)
    }

    override fun getItemCount(): Int = items.size
}

class CategoryClickListener(val clickListener: (ReadingItem.Category) -> Unit) {
    fun onClick(category: ReadingItem.Category) = clickListener(category)
}

sealed class ReadingItem {
    data class ValueElement(val label: String, val unit: String, val value: Double) : ReadingItem()

    data class Category(
        val name: String,
        var elements: List<ValueElement>,
        var isExpanded: Boolean = false
    ) : ReadingItem()
}