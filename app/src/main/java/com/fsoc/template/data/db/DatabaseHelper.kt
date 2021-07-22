package com.fsoc.template.data.db

import com.fsoc.template.data.db.entity.CustomerEntity
import com.fsoc.template.data.db.entity.UserEntity

interface DatabaseHelper {
    suspend fun getUsers(): List<UserEntity>
    suspend fun insertUser(todos: UserEntity)
    suspend fun insertCustomer(customerEntity: CustomerEntity)
    suspend fun getAllCustomer(): List<CustomerEntity>
    suspend fun getCustomerById(customerId: Long): CustomerEntity
}