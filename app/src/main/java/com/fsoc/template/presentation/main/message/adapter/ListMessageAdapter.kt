package com.fsoc.template.presentation.main.message.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fsoc.template.common.extension.loadImage
import com.fsoc.template.databinding.ItemListMessageBinding

class ListMessageAdapter(val lstMessage: ArrayList<MessageModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun addData(messageModel: MessageModel) {
        lstMessage.add(messageModel)
        notifyItemChanged(lstMessage.size - 1)
    }

    inner class ListMessageViewHolder(val binding: ItemListMessageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemListMessageBinding.inflate(LayoutInflater.from(parent.context))
        binding.root.minimumWidth = parent.measuredWidth
        return ListMessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = lstMessage[position]
        (holder as ListMessageViewHolder).binding.apply {
            tvName.text = data.name
            tvLastMessage.text = data.lastMessage
            imgProfile.loadImage(data.url)
        }
    }

    override fun getItemCount(): Int = lstMessage.size
}