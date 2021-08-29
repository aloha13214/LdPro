package com.fsoc.template.data.db.dao

import androidx.room.*
import com.fsoc.template.data.db.entity.CustomerEntity

@Dao
interface CustomerDao {
    @Query("SELECT * FROM customerentity")
    suspend fun getAllCustomer(): List<CustomerEntity>

    @Query("SELECT * FROM customerentity WHERE id = :customerId")
    suspend fun getCustomerById(customerId: Long): CustomerEntity

    @Insert
    suspend fun insertCustomer(customerEntity: CustomerEntity)

    @Delete
    suspend fun delete(customerEntity: CustomerEntity)

    @Update
    suspend fun update(customer: CustomerEntity)
}