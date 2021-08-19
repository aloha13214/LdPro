package com.fsoc.template.presentation.main.messagetemplate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fsoc.template.common.extension.click
import com.fsoc.template.databinding.ItemMessageTemplateBinding
import com.fsoc.template.domain.entity.templatemessage.MessageTemplateModel

class MessageTemplateAdapter(
    private val dataSet: ArrayList<MessageTemplateModel>,
    private val onMovieClick: (movie: MessageTemplateModel) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class MessageTemplateViewHolder(val binding: ItemMessageTemplateBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemMessageTemplateBinding.inflate(LayoutInflater.from(parent.context))
        binding.root.minimumWidth = parent.measuredWidth
        return MessageTemplateViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val data = dataSet[position]
        (viewHolder as MessageTemplateAdapter.MessageTemplateViewHolder).binding.apply {
            lblItemView.click { onMovieClick.invoke(data) }
            tvText.text = data.nameTitle
        }
    }

    override fun getItemCount(): Int = dataSet.size
}
