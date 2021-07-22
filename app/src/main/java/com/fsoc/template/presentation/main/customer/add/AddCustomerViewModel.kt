package com.fsoc.template.presentation.main.customer.add

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.fsoc.template.data.db.DatabaseHelper
import com.fsoc.template.data.db.entity.CustomerEntity
import com.fsoc.template.data.db.entity.SettingPrice
import com.fsoc.template.data.db.entity.SettingTime
import com.fsoc.template.data.db.entity.UserEntity
import com.fsoc.template.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class AddCustomerViewModel @Inject constructor(private val databaseHelper: DatabaseHelper) :
    BaseViewModel() {
    fun addUser(customerEntity: CustomerEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                databaseHelper.insertCustomer(customerEntity)
            } catch (ex: Exception) {
                Log.i("TAGGG", ex.stackTraceToString())
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
}