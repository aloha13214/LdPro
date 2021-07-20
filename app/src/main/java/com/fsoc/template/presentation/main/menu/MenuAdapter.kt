package com.fsoc.template.presentation.main.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fsoc.template.common.extension.click
import com.fsoc.template.databinding.ItemNavBinding

class MenuAdapter(private val menuList: List<MenuModel>, val listener: MenuClick) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemNavBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNavBinding.inflate(LayoutInflater.from(parent.context))
        binding.root.minimumHeight = parent.measuredHeight / 12
        binding.root.minimumWidth = parent.measuredWidth
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menu = menuList[position]
        holder.binding.label.text = menu.label
        holder.binding.description.text = menu.description
        holder.binding.icon.setImageResource(menu.icon)
        holder.binding.root.click { listener(menu) }
    }

    override fun getItemCount(): Int = menuList.size
}

typealias MenuClick = (MenuModel) -> Unit