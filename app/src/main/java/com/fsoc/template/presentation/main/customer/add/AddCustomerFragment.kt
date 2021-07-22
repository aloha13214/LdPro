package com.fsoc.template.presentation.main.customer.add

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.ViewGroup
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.common.extension.click
import com.fsoc.template.common.extension.getSettingCustomerList
import com.fsoc.template.common.extension.withViewModel
import com.fsoc.template.data.db.entity.CustomerEntity
import com.fsoc.template.data.db.entity.SettingPrice
import com.fsoc.template.data.db.entity.SettingTime
import com.fsoc.template.databinding.FragmentAddCustomerBinding
import com.fsoc.template.presentation.base.BaseFragment

class AddCustomerFragment : BaseFragment<AddCustomerViewModel, FragmentAddCustomerBinding>() {
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
        binding.btnReadContacts.click {
            startActivityReadContacts()
        }
        binding.btnAdd.click {
            handleAddCustomer()
        }
    }

    private fun handleAddCustomer() {
        val settingPrice = SettingPrice()
        val settingTime = SettingTime()
        val settingPriceString = objectToString(settingPrice) ?: return
        val settingTimeString = objectToString(settingTime) ?: return
        val customerEntity = CustomerEntity(
            customerName = binding.edtCustomerName.text.toString(),
            phoneNumber = binding.edtPhone.text.toString(),
            typeCustomer = 0,
            settingPrice = settingPriceString,
            settingTime = settingTimeString
        )
        viewModel.addUser(customerEntity)
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