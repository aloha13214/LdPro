package com.fsoc.template.data.db

import com.fsoc.template.data.db.entity.CustomerEntity
import com.fsoc.template.data.db.entity.UserEntity
import javax.inject.Inject

class DatabaseHelperImplement @Inject constructor(): DatabaseHelper {
    @Inject
    lateinit var database: AppDatabase
    override suspend fun getUsers(): List<UserEntity> {
        return database.userDao().getAll()
    }

    override suspend fun insertUser(userInsert: UserEntity) {
        return database.userDao().insertUser(userInsert)
    }

    override suspend fun insertCustomer(customerEntity: CustomerEntity) {
        return database.customerDao().insertCustomer(customerEntity)
    }

    override suspend fun getAllCustomer(): List<CustomerEntity> {
        return database.customerDao().getAllCustomer()
    }

    override suspend fun getCustomerById(customerId: Long): CustomerEntity {
        return database.customerDao().getCustomerById(customerId)
    }

    override suspend fun deleteCustomer(customer: CustomerEntity) {
        return database.customerDao().delete(customer)
    }

    override suspend fun updateCustomer(customer: CustomerEntity) {
        return database.customerDao().update(customer)
    }
}