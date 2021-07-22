package com.fsoc.template.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fsoc.template.data.db.dao.CustomerDao
import com.fsoc.template.data.db.dao.UserDao
import com.fsoc.template.data.db.entity.CustomerEntity
import com.fsoc.template.data.db.entity.UserEntity

@Database(entities = [UserEntity::class, CustomerEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object{
        const val DATABASE_NAME = "mydatabase"
    }
    abstract fun userDao(): UserDao
    abstract fun customerDao(): CustomerDao
}