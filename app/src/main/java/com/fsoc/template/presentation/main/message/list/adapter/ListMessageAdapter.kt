package com.fsoc.template.presentation.main.message.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fsoc.template.common.extension.click
import com.fsoc.template.common.extension.show
import com.fsoc.template.data.db.entity.ListMessageEntity
import com.fsoc.template.databinding.ItemListMessageBinding

class ListMessageAdapter(
    private val onItemClick: (listMessageEntity: ListMessageEntity) -> Unit,
    private val onItemAdd: (position: Int) -> Unit,
    private val onItemDelete: (position: Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val lstMessage = arrayListOf<ListMessageEntity>()

    fun setData(list: ArrayList<ListMessageEntity>) {
        lstMessage.clear()
        lstMessage.addAll(list)
        notifyDataSetChanged()
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
            tvName.text = data.title
            tvLastMessage.text = data.lastMessage
//            imgProfile.loadImage("")
            tvDelete.click { onItemClick(position) }
            imgAdd.show(!data.isAdd)
            imgAdd.click { onClickAdd(position) }
            this.root.click { onClickItem(position) }
        }
    }

    private fun onItemClick(position: Int) {
        onItemDelete.invoke(position)
    }

    private fun onClickAdd(position: Int) {
        onItemAdd.invoke(position)
    }

    private fun onClickItem(position: Int) {
        onItemClick.invoke(lstMessage[position])
    }

    override fun getItemCount(): Int = lstMessage.size
}