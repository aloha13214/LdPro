package com.fsoc.template.presentation.main.customer.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.fsoc.template.R
import com.fsoc.template.common.Resource
import com.fsoc.template.common.Status
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.common.extension.click
import com.fsoc.template.common.extension.observe
import com.fsoc.template.common.extension.showConfirmDialog
import com.fsoc.template.common.extension.withViewModel
import com.fsoc.template.data.db.entity.CustomerEntity
import com.fsoc.template.databinding.FragmentListCustomerBinding
import com.fsoc.template.presentation.base.BaseFragment

class ListCustomerFragment : BaseFragment<ListCustomerViewModel, FragmentListCustomerBinding>() {
    private var bottomSheetFragment: BottomSheetFragment? = null
    private val listCustomerAdapter by lazy {
        ListCustomerAdapter(
            { idCustomer ->
                val bundle = Bundle().apply {
                    putSerializable(MODE_KEY, Mode.Edit)
                    putLong(CUSTOMER_ID, idCustomer)
                }
                bottomSheetFragment = BottomSheetFragment {
                    bottomSheetFragment?.dismiss()
                    when (it) {
                        TypeSetting.SettingPrice -> {
                            navigate(R.id.addCustomerFragment, bundle)
                        }
                        TypeSetting.SettingTime -> {
                            navigate(R.id.nav_settingTimeFragment, bundle)
                        }
                    }
                }
                bottomSheetFragment?.show(
                    requireActivity().supportFragmentManager,
                    bottomSheetFragment?.tag
                )
            }) { customerEntity ->
            showConfirmDialog("Bạn có muốn xoá ${customerEntity.customerName} khỏi danh sách hay không?") {
                viewModel.deleteCustomer(customerEntity)
            }
        }
    }


    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun initViewModel() {
        viewModel = withViewModel(viewModelFactory) {
            observe(getCustomers(), ::observerListUser)
            observe(deleteCustomer, ::observerDeleteCustomer)
        }
    }

    private fun observerDeleteCustomer(resource: Resource<Unit>) {
        when (resource.status) {
            Status.LOADING -> {
                showLoading(true)
            }
            Status.ERROR -> {
                showLoading(false)
                resource.e?.let { showErrorMsg(it) }
            }
            Status.SUCCESS -> {
                showLoading(false)
                viewModel.getAllCustomer()
            }
        }
    }

    private fun observerListUser(resource: Resource<List<CustomerEntity>>) {
        when (resource.status) {
            Status.LOADING -> {
                showLoading(true)
            }
            Status.ERROR -> {
                showLoading(false)
                resource.e?.let { showErrorMsg(it) }
            }
            Status.SUCCESS -> {
                showLoading(false)
                resource.data?.let {
                    listCustomerAdapter.addCustomers(it)
                    binding.rcvCustomers.adapter = listCustomerAdapter
                } ?: run {}
            }
        }
    }

    override fun setUpView() {
        binding.fab.click {
            val bundle = Bundle().apply {
                putSerializable(MODE_KEY, Mode.Add)
            }
            navigate(R.id.addCustomerFragment, bundle)
        }
    }

    override fun fireData() {
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllCustomer()
    }

    override fun setUpBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListCustomerBinding = FragmentListCustomerBinding.inflate(inflater, container, false)

    companion object {
        const val MODE_KEY = "Mode"
        const val CUSTOMER_ID = "CustomerId"
    }
}

enum class Mode {
    Add,
    Edit
}