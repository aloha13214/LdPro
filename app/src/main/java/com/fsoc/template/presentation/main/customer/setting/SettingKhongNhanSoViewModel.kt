package com.fsoc.template.presentation.main.customer.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fsoc.template.common.Resource
import com.fsoc.template.data.db.DatabaseHelper
import com.fsoc.template.data.db.entity.CustomerEntity
import com.fsoc.template.data.db.entity.SettingTime
import com.fsoc.template.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class SettingKhongNhanSoViewModel @Inject constructor(val databaseHelper: DatabaseHelper): BaseViewModel() {
    var settingTime: SettingTime? = null
    var customerEntity: CustomerEntity? = null
    var customerType: Int = 0
    var idCustomer: Long? = null

    private var _customerIsPicked = MutableLiveData<Resource<CustomerEntity>>()
    var customerIsPicked: LiveData<Resource<CustomerEntity>> = _customerIsPicked

    private var _updateCustomer = MutableLiveData<Resource<Unit>>()
    val updateCustomer: LiveData<Resource<Unit>> = _updateCustomer

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
}