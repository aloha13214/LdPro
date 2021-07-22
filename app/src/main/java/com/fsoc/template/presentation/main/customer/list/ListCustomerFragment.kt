package com.fsoc.template.presentation.main.customer.list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.fsoc.template.R
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.common.extension.click
import com.fsoc.template.common.extension.withViewModel
import com.fsoc.template.databinding.FragmentListCustomerBinding
import com.fsoc.template.presentation.base.BaseFragment

class ListCustomerFragment : BaseFragment<ListCustomerViewModel, FragmentListCustomerBinding>() {
    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun initViewModel() {
        viewModel = withViewModel(viewModelFactory) {

        }
    }

    override fun setUpView() {
        binding.fab.click {
            navigate(R.id.addCustomerFragment)
        }
    }

    override fun fireData() {
    }

    override fun setUpBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListCustomerBinding = FragmentListCustomerBinding.inflate(inflater, container, false)

}