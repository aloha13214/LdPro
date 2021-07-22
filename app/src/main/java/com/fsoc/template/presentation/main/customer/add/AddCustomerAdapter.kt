package com.fsoc.template.presentation.main.customer.add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fsoc.template.common.extension.addSimpleTextWatcher
import com.fsoc.template.databinding.ItemHeaderRecyclerviewBinding
import com.fsoc.template.databinding.ItemSettingCustomerBinding

enum class TYPE(val type: Int) {
    HEADER(0),
    NORMAL(1)
}

class AddCustomerAdapter(val addSettingCustomerModelList: List<AddSettingCustomerModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0, 6, 8, 13 -> {
                TYPE.HEADER.type
            }
            else -> {
                TYPE.NORMAL.type
            }
        }
    }

    inner class HeaderViewHolder(val binding: ItemHeaderRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class NormalViewHolder(val binding: ItemSettingCustomerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE.HEADER.type -> {
                val binding =
                    ItemHeaderRecyclerviewBinding.inflate(LayoutInflater.from(parent.context))
                binding.root.minimumWidth = parent.measuredWidth
                HeaderViewHolder(binding)
            }
            else -> {
                val binding =
                    ItemSettingCustomerBinding.inflate(LayoutInflater.from(parent.context))
                binding.root.minimumWidth = parent.measuredWidth
                NormalViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int = addSettingCustomerModelList.size
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val settingModel = addSettingCustomerModelList[position]
        if (holder is HeaderViewHolder) {
            holder.binding.headerName.text = settingModel.typePrize
        } else {
            val binding = (holder as NormalViewHolder).binding
            binding.typePrize.text = settingModel.typePrize
            binding.valuePrize.setText(settingModel.valuePrize.toString())
            binding.counter.setText(settingModel.counter.toString())
            binding.valuePrize.addSimpleTextWatcher {
                if (it.toString().isNotBlank()) {
                    addSettingCustomerModelList[position].valuePrize = it.toString().toFloatOrNull()
                }
            }
            binding.counter.addSimpleTextWatcher {
                if (it.toString().isNotBlank()) {
                    addSettingCustomerModelList[position].counter = it.toString().toFloatOrNull()
                }
            }
        }
    }
}