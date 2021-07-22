package com.fsoc.template.presentation.main.customer.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fsoc.template.common.ClickDeleteCustomer
import com.fsoc.template.common.ClickOnCustomer
import com.fsoc.template.common.extension.click
import com.fsoc.template.data.db.entity.CustomerEntity
import com.fsoc.template.databinding.ItemCustomerBinding

class ListCustomerAdapter(
    val clickOnCustomer: ClickOnCustomer,
    val clickDeleteCustomer: ClickDeleteCustomer
) : RecyclerView.Adapter<ListCustomerAdapter.ViewHolder>() {
    private var customers: ArrayList<CustomerEntity> = arrayListOf()
    fun addCustomers(customers: List<CustomerEntity>) {
        this.customers.clear()
        this.customers.addAll(customers)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemCustomerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCustomerBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        val customer = customers[position]
        binding.apply {
            name.text = customer.customerName
            phoneNumber.text = customer.phoneNumber
            root.click { clickOnCustomer(customer.id) }
            ivDelete.click { clickDeleteCustomer(customer) }
        }
    }

    override fun getItemCount(): Int = customers.size
}