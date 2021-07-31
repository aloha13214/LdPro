package com.fsoc.template.presentation.main.customer.setting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.fsoc.template.common.Resource
import com.fsoc.template.common.Status
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.common.extension.*
import com.fsoc.template.data.db.entity.CustomerEntity
import com.fsoc.template.databinding.FragmentMaxKhongBinding
import com.fsoc.template.presentation.base.BaseFragment
import com.fsoc.template.presentation.main.customer.list.ListCustomerFragment

class SettingKhongNhanSoFragment :
    BaseFragment<SettingKhongNhanSoViewModel, FragmentMaxKhongBinding>() {
    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun initViewModel() {
        viewModel = withViewModel(viewModelFactory) {
            observe(customerIsPicked, ::observerCustomerIsPicked)
            observe(updateCustomer, ::observeUpdateUser)
        }
    }

    private fun observeUpdateUser(resource: Resource<Unit>) {
        when (resource.status) {
            Status.LOADING -> {
                showLoading(true)
            }
            Status.SUCCESS -> {
                hideKeyboard()
                showLoading(false)
                showSnackbar("Bạn đã cập nhật thành công!")
                navigateUp()
            }
            Status.ERROR -> {
                showLoading(false)
                resource.e?.fillInStackTrace()?.let { showErrorMsg(it) }
            }
        }
    }

    private fun observerCustomerIsPicked(resource: Resource<CustomerEntity>) {
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
                resource.data?.let { settingEditCustomer(it) }
            }
        }
    }

    private fun settingEditCustomer(customerEntity: CustomerEntity) {
        viewModel.customerEntity = customerEntity
        viewModel.settingTime = stringToObject(customerEntity.settingTime) ?: return
        val settingTime = viewModel.settingTime ?: return
        binding.apply {
            edtDanDe.setText(settingTime.danDe)
            edtDanLo.setText(settingTime.danLo)
            edtGiuXien2.setText(if (settingTime.xien2 != 0) settingTime.xien2.toString() else "")
            edtGiuXien3.setText(if (settingTime.xien3 != 0) settingTime.xien3.toString() else "")
            edtGiuXien4.setText(if (settingTime.xien4 != 0) settingTime.xien4.toString() else "")
            edtGiu3Cang.setText(if (settingTime.cang != 0) settingTime.cang.toString() else "")
        }
    }

    override fun setUpView() {
        binding.btnSave.click {
            handleSave()
        }
        binding.edtDanDe.addSimpleTextWatcher {
            toggleClear(it, binding.ivClearDe)
        }
        binding.edtDanLo.addSimpleTextWatcher {
            toggleClear(it, binding.ivClearDanLo)
        }
        binding.edtGiuXien2.addSimpleTextWatcher {
            toggleClear(it, binding.ivClearXien2)
        }
        binding.edtGiuXien3.addSimpleTextWatcher {
            toggleClear(it, binding.ivClearXien3)
        }
        binding.edtGiuXien4.addSimpleTextWatcher {
            toggleClear(it, binding.ivClearXien4)
        }
        binding.edtGiu3Cang.addSimpleTextWatcher {
            toggleClear(it, binding.ivClearCang)
        }
        binding.ivClearDe.click { binding.edtDanDe.setText("") }
        binding.ivClearDanLo.click { binding.edtDanLo.setText("") }
        binding.ivClearXien2.click { binding.edtGiuXien2.setText("") }
        binding.ivClearXien3.click { binding.edtGiuXien3.setText("") }
        binding.ivClearXien4.click { binding.edtGiuXien4.setText("") }
        binding.ivClearCang.click { binding.edtGiu3Cang.setText("") }
    }

    private fun toggleClear(text: CharSequence?, imageClear: ImageView) {
        text?.let {
            if (text.isEmpty()) {
                imageClear.visibility = View.INVISIBLE
            } else {
                imageClear.visibility = View.VISIBLE
            }
        }
    }

    override fun fireData() {
        arguments?.let {
            viewModel.idCustomer = it.getLong(ListCustomerFragment.CUSTOMER_ID)
        }
        viewModel.findCustomer()
    }

    private fun handleSave() {
        val danDe = binding.edtDanDe.text.toString()
        val danLo = binding.edtDanLo.text.toString()
        val giuXien2 = binding.edtGiuXien2.text.toString().toIntOrNull() ?: 0
        val giuXien3 = binding.edtGiuXien3.text.toString().toIntOrNull() ?: 0
        val giuXien4 = binding.edtGiuXien4.text.toString().toIntOrNull() ?: 0
        val giu3Cang = binding.edtGiu3Cang.text.toString().toIntOrNull() ?: 0
        val settingTime = viewModel.settingTime ?: return
        val newSettingTime = settingTime.copy(
            danDe = danDe,
            danLo = danLo,
            xien2 = giuXien2,
            xien3 = giuXien3,
            xien4 = giuXien4,
            cang = giu3Cang
        )
        val newSettingTimeString = objectToString(newSettingTime) ?: return
        viewModel.customerEntity?.let {
            val newCustomerEntity = it.copy(settingTime = newSettingTimeString)
            viewModel.updateCustomer(newCustomerEntity)
        }
    }

    override fun setUpBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMaxKhongBinding =
        FragmentMaxKhongBinding.inflate(inflater, container, false)
}