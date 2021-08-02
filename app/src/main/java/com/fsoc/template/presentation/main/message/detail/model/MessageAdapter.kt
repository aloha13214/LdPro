package com.fsoc.template.presentation.main.message.detail.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fsoc.template.common.extension.addSimpleTextWatcher
import com.fsoc.template.data.db.entity.MessageEntity
import com.fsoc.template.databinding.MyMessageBinding
import com.fsoc.template.databinding.TheirMessageBinding
import com.fsoc.template.presentation.main.customer.add.AddCustomerAdapter

enum class MESSAGE(val type: Int) {
    MY_MESSAGE(0),
    THEIR_MESSAGE(1)
}

class MessageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return when {
            lstMessage[position].isUser -> {
                MESSAGE.MY_MESSAGE.type
            }
            else -> {
                MESSAGE.THEIR_MESSAGE.type
            }
        }
    }

    private val lstMessage = arrayListOf<MessageEntity>()

    fun setData(list: ArrayList<MessageEntity>) {
        lstMessage.clear()
        lstMessage.addAll(list)
        notifyDataSetChanged()
    }

    inner class MyMessageViewHolder(val binding: MyMessageBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class TheirMessageViewHolder(val binding: TheirMessageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MESSAGE.MY_MESSAGE.type -> {
                val binding =
                    MyMessageBinding.inflate(LayoutInflater.from(parent.context))
                binding.root.minimumWidth = parent.measuredWidth
                MyMessageViewHolder(binding)
            }
            else -> {
                val binding = TheirMessageBinding.inflate(LayoutInflater.from(parent.context))
                binding.root.minimumWidth = parent.measuredWidth
                TheirMessageViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = lstMessage[position]
        if (holder is MyMessageViewHolder) {
            holder.binding.content.text = data.content
        } else {
            val binding = (holder as TheirMessageViewHolder).binding
            binding.content.text = data.content

        }
    }

    override fun getItemCount(): Int = lstMessage.size
}