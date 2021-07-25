package com.fsoc.template.presentation.main.customer.setting

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
import com.fsoc.template.common.Resource
import com.fsoc.template.common.Status
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.common.extension.*
import com.fsoc.template.data.db.entity.CustomerEntity
import com.fsoc.template.data.db.entity.SettingTime
import com.fsoc.template.databinding.FragmentSettingTimeBinding
import com.fsoc.template.presentation.base.BaseFragment
import com.fsoc.template.presentation.main.customer.add.AddCustomerViewModel
import com.fsoc.template.presentation.main.customer.list.ListCustomerFragment
import com.fsoc.template.presentation.main.customer.list.Mode
import java.lang.Exception
import java.util.*

class SettingTimeFragment : BaseFragment<AddCustomerViewModel, FragmentSettingTimeBinding>() {
    private var timeLoXienPickerDialog: TimePickerDialog? = null
    private var timeDeCangPickerDialog: TimePickerDialog? = null

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun initViewModel() {
        viewModel = withViewModel(viewModelFactory) {
            observe(mode, ::observerMode)
            observe(customerIsPicked, ::observerCustomerIsPicked)
            observe(updateCustomer, ::observeUpdateUser)
        }
    }

    private fun observerMode(mode: Mode?) {
        viewModel.findCustomer()
    }

    private fun observeUpdateUser(resource: Resource<Unit>) {
        when (resource.status) {
            Status.LOADING -> {
                showLoading(true)
            }
            Status.SUCCESS -> {
                showLoading(false)
                showSnackbar("Bạn đã cập nhật thành công!")
            }
            Status.ERROR -> {
                showLoading(false)
                resource.e?.fillInStackTrace()?.let { showErrorMsg(it) }
            }
        }
    }

    override fun setUpView() {
        setUpSpinnerListener()
        setUpClickListener()
        setUpSeekbarListener()
    }

    private fun setUpSeekbarListener() {
        binding.deDly.addSimpleSeekbarListener(
            {
                binding.ptGiuDeDly.text = it
            }, {
                checkSettingTimeModelNotNull()
                viewModel.settingTime!!.dlgiu_de = it
            })
        binding.loDly.addSimpleSeekbarListener(
            {
                binding.ptGiuLoDly.text = it
            }, {
                checkSettingTimeModelNotNull()
                viewModel.settingTime!!.dlgiu_lo = it
            })
        binding.xiDly.addSimpleSeekbarListener(
            {
                binding.ptGiuXienDly.text = it
            }, {
                checkSettingTimeModelNotNull()
                viewModel.settingTime!!.dlgiu_xi = it
            })
        binding.bcDly.addSimpleSeekbarListener(
            {
                binding.ptGiuBcDly.text = it
            }, {
                checkSettingTimeModelNotNull()
                viewModel.settingTime!!.dlgiu_bc = it
            })
        binding.deKhach.addSimpleSeekbarListener(
            {
                binding.ptGiuDeKhach.text = it
            }, {
                checkSettingTimeModelNotNull()
                viewModel.settingTime!!.khgiu_de = it
            })
        binding.loKhach.addSimpleSeekbarListener(
            {
                binding.ptGiuLoKhach.text = it
            }, {
                checkSettingTimeModelNotNull()
                viewModel.settingTime!!.khgiu_lo = it
            })
        binding.xiKhach.addSimpleSeekbarListener(
            {
                binding.ptGiuXienKhach.text = it
            }, {
                checkSettingTimeModelNotNull()
                viewModel.settingTime!!.khgiu_xi = it
            })
        binding.bcKhach.addSimpleSeekbarListener(
            {
                binding.ptGiuBcKhach.text = it
            }, {
                checkSettingTimeModelNotNull()
                viewModel.settingTime!!.khgiu_bc = it
            })
    }

    private fun setUpClickListener() {
        binding.btnSave.click {
            viewModel.customerEntity?.let {
                val settingTime = viewModel.settingTime ?: return@click
                val settingTimeString = objectToString(settingTime) ?: return@click
                it.settingTime = settingTimeString
                viewModel.updateCustomer(it)
            }
        }
        binding.frTimeLoXien.click {
            timeLoXienPickerDialog?.show()
        }
        binding.frTimeDeCang.click {
            timeDeCangPickerDialog?.show()
        }
    }

    private fun setUpSpinnerListener() {
        binding.spTraloitn.addSimpleOnItemSelectedListener {
            checkSettingTimeModelNotNull()
            viewModel.settingTime!!.ok_tin = it
        }
        binding.spNhanXien.addSimpleOnItemSelectedListener {
            checkSettingTimeModelNotNull()
            viewModel.settingTime!!.xien_nhan = it
        }
        binding.spBaoloidonvi.addSimpleOnItemSelectedListener {
            checkSettingTimeModelNotNull()
            viewModel.settingTime!!.loi_donvi = it
        }
        binding.spChotSodu.addSimpleOnItemSelectedListener {
            checkSettingTimeModelNotNull()
            viewModel.settingTime!!.chot_sodu = it
        }
        binding.spKhachde.addSimpleOnItemSelectedListener {
            checkSettingTimeModelNotNull()
            viewModel.settingTime!!.khach_de = it
        }
        binding.spHesode.addSimpleOnItemSelectedListener {
            checkSettingTimeModelNotNull()
            viewModel.settingTime!!.heso_de = it
        }
    }

    private fun checkSettingTimeModelNotNull() {
        if (viewModel.settingTime == null) return
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

    @SuppressLint("SetTextI18n")
    private fun settingEditCustomer(customerEntity: CustomerEntity) {
        viewModel.customerEntity = customerEntity
        viewModel.settingTime = stringToObject<SettingTime>(customerEntity.settingTime)
        if (viewModel.settingTime == null) return
        binding.apply {
            tvCustomerName.text = customerEntity.customerName
            spTraloitn.setSelection(viewModel.settingTime!!.ok_tin)
            spNhanXien.setSelection(viewModel.settingTime!!.xien_nhan)
            spChotSodu.setSelection(viewModel.settingTime!!.chot_sodu)
            spBaoloidonvi.setSelection(viewModel.settingTime!!.loi_donvi)
            spKhachde.setSelection(viewModel.settingTime!!.khach_de)
            spHesode.setSelection(viewModel.settingTime!!.heso_de)
            tvTimeLoXien.text = viewModel.settingTime!!.tg_loxien
            tvTimeDeCang.text = viewModel.settingTime!!.tg_debc
            deDly.progress = viewModel.settingTime!!.dlgiu_de/INTERVAL_SEEKBAR
            ptGiuDeDly.text = "${viewModel.settingTime!!.dlgiu_de}%"
            loDly.progress = viewModel.settingTime!!.dlgiu_lo/INTERVAL_SEEKBAR
            ptGiuLoDly.text = "${viewModel.settingTime!!.dlgiu_lo}%"
            xiDly.progress = viewModel.settingTime!!.dlgiu_xi/INTERVAL_SEEKBAR
            ptGiuXienDly.text = "${viewModel.settingTime!!.dlgiu_xi}%"
            bcDly.progress = viewModel.settingTime!!.dlgiu_bc/INTERVAL_SEEKBAR
            ptGiuBcDly.text = "${viewModel.settingTime!!.dlgiu_bc}%"
            deKhach.progress = viewModel.settingTime!!.khgiu_de/INTERVAL_SEEKBAR
            ptGiuDeKhach.text = "${viewModel.settingTime!!.khgiu_de}%"
            loKhach.progress = viewModel.settingTime!!.khgiu_lo/INTERVAL_SEEKBAR
            ptGiuLoKhach.text = "${viewModel.settingTime!!.khgiu_lo}%"
            xiKhach.progress = viewModel.settingTime!!.khgiu_xi/INTERVAL_SEEKBAR
            ptGiuXienKhach.text = "${viewModel.settingTime!!.khgiu_xi}%"
            bcKhach.progress = viewModel.settingTime!!.khgiu_bc/INTERVAL_SEEKBAR
            ptGiuBcKhach.text = "${viewModel.settingTime!!.khgiu_bc}%"
        }
        setUpTimeDialog(viewModel.settingTime!!)
    }

    private fun setUpTimeDialog(settingTime: SettingTime) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minuteCurrent = calendar.get(Calendar.MINUTE)
        var timeLoXien = listOf<String>()
        var timeDeCang = listOf<String>()
        try {
            timeLoXien = settingTime.tg_loxien.split(":")
            timeLoXienPickerDialog = TimePickerDialog(
                requireContext(),
                { view, hourOfDay, minute ->
                    val result: String = formatResultTimePicker(minute, hourOfDay)
                    viewModel.settingTime!!.tg_loxien = result
                    binding.tvTimeLoXien.text = result
                    showSnackbar("Không nhận lô/xiên sau $result")
                },
                timeLoXien.first().toIntOrNull() ?: hour,
                timeLoXien.last().toIntOrNull() ?: minuteCurrent,
                true
            )
        } catch (ex: Exception) {
            throw ex
        }
        try {
            timeDeCang = settingTime.tg_debc.split(":")
            timeDeCangPickerDialog = TimePickerDialog(
                requireContext(),
                { view, hourOfDay, minute ->
                    val result: String = formatResultTimePicker(minute, hourOfDay)
                    viewModel.settingTime!!.tg_debc = result
                    binding.tvTimeDeCang.text = result
                    showSnackbar("Không nhận đề/càng sau $result")
                },
                timeDeCang.first().toIntOrNull() ?: hour,
                timeDeCang.last().toIntOrNull() ?: minuteCurrent,
                true
            )
        } catch (ex: Exception) {
            throw ex
        }
    }

    private fun formatResultTimePicker(minute: Int, hourOfDay: Int): String {
        return if (minute < 10) {
            "$hourOfDay:0$minute"
        } else {
            "$hourOfDay:$minute"
        }
    }

    override fun fireData() {
        arguments?.let {
            viewModel.mode.value = it.getSerializable(ListCustomerFragment.MODE_KEY) as? Mode
            viewModel.idCustomer = it.getLong(ListCustomerFragment.CUSTOMER_ID)
        }
    }

    override fun setUpBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingTimeBinding = FragmentSettingTimeBinding.inflate(inflater, container, false)

}