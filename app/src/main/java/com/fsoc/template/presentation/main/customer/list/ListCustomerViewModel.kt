package com.fsoc.template.presentation.main.customer.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fsoc.template.common.Resource
import com.fsoc.template.data.db.DatabaseHelper
import com.fsoc.template.data.db.entity.CustomerEntity
import com.fsoc.template.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class ListCustomerViewModel @Inject constructor(private val databaseHelper: DatabaseHelper) :
    BaseViewModel() {

    private var _customers = MutableLiveData<Resource<List<CustomerEntity>>>()
    private var _deleteCustomer = MutableLiveData<Resource<Unit>>()
    var deleteCustomer: LiveData<Resource<Unit>> = _deleteCustomer

    fun getAllCustomer() {
        viewModelScope.launch(Dispatchers.IO) {
            _customers.postValue(Resource.loading(null))
            try {
                val result = databaseHelper.getAllCustomer()
                _customers.postValue(Resource.success(result))
            } catch (ex: Exception) {
                _customers.postValue(Resource.error(ex.fillInStackTrace(), null))
            }
        }
    }

    fun getCustomers(): LiveData<Resource<List<CustomerEntity>>> = _customers

    fun deleteCustomer(customerEntity: CustomerEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            _deleteCustomer.postValue(Resource.loading(null))
            try {
                val result = databaseHelper.deleteCustomer(customerEntity)
                _deleteCustomer.postValue(Resource.success(result))
            } catch (ex: Exception) {
                _deleteCustomer.postValue(Resource.error(ex.fillInStackTrace(), null))
            }
        }
    }
}