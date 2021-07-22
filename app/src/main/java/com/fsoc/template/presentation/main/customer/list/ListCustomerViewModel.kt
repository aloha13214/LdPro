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
    fun findCustomer(idCustomer: Long) {
        viewModelScope.launch {
            try {
                val customerIsPicked = databaseHelper.getCustomerById(idCustomer)
                Log.i("ListCustomerViewModel", customerIsPicked.toString())
            }catch (ex: Exception) {

            }
        }
    }
}