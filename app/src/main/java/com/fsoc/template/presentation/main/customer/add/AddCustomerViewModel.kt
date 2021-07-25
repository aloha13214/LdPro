package com.fsoc.template.presentation.main.customer.add

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fsoc.template.common.Resource
import com.fsoc.template.common.extension.checkRegex
import com.fsoc.template.common.extension.regexPhone
import com.fsoc.template.data.db.DatabaseHelper
import com.fsoc.template.data.db.entity.CustomerEntity
import com.fsoc.template.data.db.entity.SettingTime
import com.fsoc.template.presentation.base.BaseViewModel
import com.fsoc.template.presentation.main.customer.list.Mode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class AddCustomerViewModel @Inject constructor(private val databaseHelper: DatabaseHelper) :
    BaseViewModel() {

    var settingTime: SettingTime? = null
    var customerEntity: CustomerEntity? = null
    var customerType: Int = 0
    var mode = MutableLiveData<Mode>(null)
    var idCustomer: Long? = null
    private var _insertUser = MutableLiveData<Resource<Unit>>()
    val insertUser: LiveData<Resource<Unit>> = _insertUser

    private var _customerIsPicked = MutableLiveData<Resource<CustomerEntity>>()
    var customerIsPicked: LiveData<Resource<CustomerEntity>> = _customerIsPicked

    private var _updateCustomer = MutableLiveData<Resource<Unit>>()
    val updateCustomer: LiveData<Resource<Unit>> = _updateCustomer
    fun isAddType(mode: Mode?): Boolean = mode == null || mode == Mode.Add

    fun insertUser(customerEntity: CustomerEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            _insertUser.postValue(Resource.loading(null))
            try {
                val result = databaseHelper.insertCustomer(customerEntity)
                _insertUser.postValue(Resource.success(result))
            } catch (ex: Exception) {
                _insertUser.postValue(Resource.error(ex.fillInStackTrace(), null))
            }
        }
    }

    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            val users = databaseHelper.getAllCustomer()
            users.forEach {
                print("$it \n")
            }
        }
    }

    fun findCustomer() {
        val id = idCustomer ?: return
        viewModelScope.launch(Dispatchers.IO) {
            _customerIsPicked.postValue(Resource.loading(null))
            try {
                val customerIsPicked = databaseHelper.getCustomerById(id)
                _customerIsPicked.postValue(Resource.success(customerIsPicked))
            } catch (ex: Exception) {
                _customerIsPicked.postValue(Resource.error(ex.fillInStackTrace(), null))
            }
        }
    }

    fun updateCustomer(customerEntity: CustomerEntity) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                _updateCustomer.postValue(Resource.loading(null))
                val result = databaseHelper.updateCustomer(customerEntity)
                _updateCustomer.postValue(Resource.success(result))
            }catch (ex: Exception) {
                _updateCustomer.postValue(Resource.error(ex.fillInStackTrace(), null))
            }
        }
    }

    fun validateCustomerName(customerName: String): Boolean = customerName.isNotBlank()
    fun validatePhoneNumber(phone: String) = checkRegex(regexPhone, phone)
}