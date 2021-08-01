package com.fsoc.template.presentation.main.customer.add

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.ViewGroup
import com.fsoc.template.R
import com.fsoc.template.common.Resource
import com.fsoc.template.common.Status
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.common.extension.click
import com.fsoc.template.common.extension.observe
import com.fsoc.template.common.extension.withViewModel
import com.fsoc.template.data.db.entity.CustomerEntity
import com.fsoc.template.data.db.entity.SettingPrice
import com.fsoc.template.data.db.entity.SettingTime
import com.fsoc.template.databinding.FragmentAddCustomerBinding
import com.fsoc.template.presentation.base.BaseFragment
import com.fsoc.template.presentation.main.customer.list.ListCustomerFragment.Companion.CUSTOMER_ID
import com.fsoc.template.presentation.main.customer.list.ListCustomerFragment.Companion.MODE_KEY
import com.fsoc.template.presentation.main.customer.list.Mode
import com.fsoc.template.presentation.main.message.list.MessageListFragment
import com.fsoc.template.presentation.main.message.list.adapter.MessageModel

enum class CustomerType(val customerType: Int) {
    Guess(0),
    Owner(1),
    Both(2)
}

class AddCustomerFragment : BaseFragment<AddCustomerViewModel, FragmentAddCustomerBinding>() {

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun initViewModel() {
        viewModel = withViewModel(viewModelFactory) {
            observe(mode, ::observerMode)
            observe(customerIsPicked, ::observerCustomerIsPicked)
            observe(insertUser, ::observeInsertUser)
            observe(updateCustomer, ::observeInsertUser)
        }
    }

    private fun observeInsertUser(resource: Resource<Unit>) {
        when (resource.status) {
            Status.LOADING -> {
                showLoading(true)
            }
            Status.SUCCESS -> {
                showLoading(false)
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


    private fun observerMode(mode: Mode?) {
        if (viewModel.isAddType(mode)) {
            bindDataSettingPrice(SettingPrice())
            binding.btnAdd.text = getString(R.string.str_add)
            binding.rgChooseType.check(R.id.rb_guess)
        } else {
            viewModel.findCustomer()
            binding.btnAdd.text = getString(R.string.str_edit)
        }
    }

//    private fun isAddType(mode: Mode?): Boolean = mode == null || mode == Mode.Add

    private fun settingEditCustomer(customerEntity: CustomerEntity) {
        viewModel.settingTime = stringToObject(customerEntity.settingTime)
        viewModel.customerEntity = customerEntity
        binding.edtCustomerName.setText(customerEntity.customerName)
        binding.edtPhone.setText(customerEntity.phoneNumber)
        val settingPrice = stringToObject<SettingPrice>(customerEntity.settingPrice) ?: return
        bindDataSettingPrice(settingPrice)
        setUpTypeCustomer(customerEntity.typeCustomer)
    }

    private fun setUpTypeCustomer(typeCustomer: Int) {
        when (typeCustomer) {
            CustomerType.Guess.customerType -> {
                binding.rgChooseType.check(R.id.rb_guess)
            }
            CustomerType.Owner.customerType -> {
                binding.rgChooseType.check(R.id.rb_owner)
            }
            CustomerType.Both.customerType -> {
                binding.rgChooseType.check(R.id.rb_both)
            }
        }
    }

    private fun bindDataSettingPrice(settingPrice: SettingPrice) {
        binding.apply {
            edtDea.setText(settingPrice.dea)
            anDea.setText(settingPrice.an_dea)
            edtDeb.setText(settingPrice.deb)
            anDeb.setText(settingPrice.an_deb)
            edtDec.setText(settingPrice.dec)
            anDec.setText(settingPrice.an_dec)
            edtDed.setText(settingPrice.ded)
            anDed.setText(settingPrice.an_ded)
            edtDet.setText(settingPrice.det)
            anDet.setText(settingPrice.an_det)
            edtLo.setText(settingPrice.lo)
            anLo.setText(settingPrice.an_lo)
            edtGiax2.setText(settingPrice.gia_x2)
            anX2.setText(settingPrice.an_x2)
            edtGiax3.setText(settingPrice.gia_x3)
            anX3.setText(settingPrice.an_x3)
            edtGiax4.setText(settingPrice.gia_x4)
            anX4.setText(settingPrice.an_x4)
            edtGiaxn.setText(settingPrice.gia_xn)
            anXn.setText(settingPrice.an_xn)
            edtBc.setText(settingPrice.gia_bc)
            anBc.setText(settingPrice.an_bc)
        }
    }


    override fun setUpView() {
        binding.btnReadContacts.click {
            startActivityReadContacts()
        }
        binding.btnAdd.click {
            if (viewModel.isAddType(viewModel.mode.value)) {
                handleAddCustomer()
            } else {
                handleEditCustomer()
            }
        }
        binding.rgChooseType.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_guess -> {
                    viewModel.customerType = CustomerType.Guess.customerType
                }
                R.id.rb_owner -> {
                    viewModel.customerType = CustomerType.Owner.customerType
                }
                R.id.rb_both -> {
                    viewModel.customerType = CustomerType.Both.customerType
                }
            }
        }
    }

    private fun handleEditCustomer() {
        val customerEntity = prepareDataForInsertOrUpdate() ?: return
        viewModel.updateCustomer(customerEntity)
    }

    private fun prepareDataForInsertOrUpdate(): CustomerEntity? {
        val customerName = binding.edtCustomerName.text.toString().trim()
        if (!viewModel.validateCustomerName(customerName)) {
            showErrorDialog(getString(R.string.str_validate_customer_name))
            return null
        }
        val phone = binding.edtPhone.text.toString().trim()
        if (!viewModel.validatePhoneNumber(phone)) {
            showErrorDialog(getString(R.string.str_validate_phone))
            return null
        }
        val settingPrice = getSettingPrice()
        val settingTime = viewModel.settingTime ?: SettingTime()
        val settingPriceString = objectToString(settingPrice) ?: return null
        val settingTimeString = objectToString(settingTime) ?: return null
        return if (viewModel.isAddType(viewModel.mode.value)) {
            CustomerEntity(
                customerName = customerName,
                phoneNumber = phone,
                typeCustomer = viewModel.customerType,
                settingPrice = settingPriceString,
                settingTime = settingTimeString
            )
        } else {
            viewModel.idCustomer?.let { id ->
                CustomerEntity(
                    id,
                    customerName,
                    phone,
                    viewModel.customerType,
                    settingPriceString,
                    settingTimeString
                )
            }
        }
    }

    private fun handleAddCustomer() {
        val customerEntity = prepareDataForInsertOrUpdate() ?: return
        viewModel.insertUser(customerEntity)
    }

    private fun getSettingPrice(): SettingPrice {
        return SettingPrice(
            binding.edtDea.text.toString(),
            binding.anDea.text.toString(),
            binding.edtDeb.text.toString(),
            binding.anDeb.text.toString(),
            binding.edtDec.text.toString(),
            binding.anDec.text.toString(),
            binding.edtDed.text.toString(),
            binding.anDed.text.toString(),
            binding.edtDet.text.toString(),
            binding.anDet.text.toString(),
            binding.edtLo.text.toString(),
            binding.anLo.text.toString(),
            binding.edtGiax2.text.toString(),
            binding.anX2.text.toString(),
            binding.edtGiax3.text.toString(),
            binding.anX3.text.toString(),
            binding.edtGiax4.text.toString(),
            binding.anX4.text.toString(),
            binding.edtGiaxn.text.toString(),
            binding.anXn.text.toString(),
            binding.edtBc.text.toString(),
            binding.anBc.text.toString()
        )
    }

    override fun fireData() {
        arguments?.let {
            viewModel.mode.value = it.getSerializable(MODE_KEY) as? Mode
            viewModel.idCustomer = it.getLong(CUSTOMER_ID)
            viewModel.modelMessage =
                it.getSerializable(MessageListFragment.KEY_MESSAGE_ADD_CUSTOMER) as? MessageModel
        }

        if (viewModel.modelMessage != null) {
            binding.edtCustomerName.apply {
                isClickable = false
                setText(viewModel.modelMessage?.title)
            }
            binding.edtPhone.apply {
                isClickable = false
                setText(viewModel.modelMessage?.title)
            }
            binding.btnReadContacts.isClickable = false
        }
    }

    override fun setUpBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddCustomerBinding = FragmentAddCustomerBinding.inflate(inflater, container, false)

    private fun startActivityReadContacts() {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_CONTACT)
    }

    @SuppressLint("Recycle")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_CONTACT && resultCode == Activity.RESULT_OK) {
            val contactData = data?.data ?: return
            val cursor =
                requireActivity().contentResolver.query(contactData, null, null, null, null)
                    ?: return
            if (cursor.moveToFirst()) {
                val displayName =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val columnIndexPhone = cursor.getColumnIndex(ContactsContract.Contacts.Data.DATA1)
                val numberPhone = cursor.getString(columnIndexPhone)
                binding.edtCustomerName.setText(displayName)
                binding.edtPhone.setText(numberPhone)
            }
        }
    }

    companion object {
        const val REQUEST_CODE_CONTACT = 101
    }
}