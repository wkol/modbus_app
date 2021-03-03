package com.example.modbus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.modbus.databinding.CatergoryRowLayoutBinding
import com.example.modbus.databinding.ReadingRowLayoutBinding

class CategoryAdapter(val categories: MutableList<Category>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(val binding: CatergoryRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Category) {
            binding.category = data
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CatergoryRowLayoutBinding.inflate(inflater)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
        val adapter = ValueAdapter(categories[position].elements.toMutableList())
        holder.binding.readingRecycler.adapter = adapter
        holder.binding.textView.setOnClickListener {
            if(categories[position].isExpanded) {
                collapseCategory(position, holder.binding)
            } else {
                expandCategory(position, holder.binding)
            }
        }
        holder.binding.imgArrow.setOnClickListener {
            if(categories[position].isExpanded) {
                collapseCategory(position, holder.binding)
            } else {
                expandCategory(position, holder.binding)
            }
        }
    }


    private fun expandCategory(position: Int, binding: CatergoryRowLayoutBinding) {
        binding.readingRecycler.visibility = View.VISIBLE
        categories[position].isExpanded = true
    }

    private fun collapseCategory(position: Int, binding: CatergoryRowLayoutBinding) {
        binding.readingRecycler.visibility = View.GONE
        categories[position].isExpanded = false
    }

    override fun getItemCount(): Int = categories.size

}

class ValueAdapter(var values: MutableList<ValueElement>) :
        RecyclerView.Adapter<ValueAdapter.ValuesViewHolder>() {
    inner class ValuesViewHolder(val binding: ReadingRowLayoutBinding): RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(data: ValueElement) {
            binding.valueElement = data
            binding.executePendingBindings()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ValuesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ReadingRowLayoutBinding.inflate(inflater)
        return ValuesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ValuesViewHolder, position: Int) = holder.bind(values[position])

    override fun getItemCount(): Int = values.size

        }
