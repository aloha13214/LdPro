package com.fsoc.template.presentation.main.customer.add

import android.view.LayoutInflater
import android.view.ViewGroup
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.common.extension.click
import com.fsoc.template.common.extension.getSettingCustomerList
import com.fsoc.template.common.extension.withViewModel
import com.fsoc.template.databinding.FragmentAddCustomerBinding
import com.fsoc.template.presentation.base.BaseFragment

class AddCustomerFragment: BaseFragment<AddCustomerViewModel, FragmentAddCustomerBinding>() {
    private lateinit var adapter: AddCustomerAdapter

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun initViewModel() {
        viewModel = withViewModel(viewModelFactory) {

        }
    }

    override fun setUpView() {
        setUpListSetting()
        binding.btnAdd.click {
            val a = adapter.addSettingCustomerModelList
        }
    }

    private fun setUpListSetting() {
        val addSettingCustomerModelList = getSettingCustomerList()
        adapter = AddCustomerAdapter(addSettingCustomerModelList)
        binding.rcvSettingCustomer.adapter = adapter
    }

    override fun fireData() {

    }

    override fun setUpBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddCustomerBinding = FragmentAddCustomerBinding.inflate(inflater, container, false)
}