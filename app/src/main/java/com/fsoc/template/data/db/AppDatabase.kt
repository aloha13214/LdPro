package com.fsoc.template.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fsoc.template.data.db.dao.CustomerDao
import com.fsoc.template.data.db.dao.ListMessageDao
import com.fsoc.template.data.db.dao.MessageDao
import com.fsoc.template.data.db.dao.UserDao
import com.fsoc.template.data.db.entity.CustomerEntity
import com.fsoc.template.data.db.entity.ListMessageEntity
import com.fsoc.template.data.db.entity.MessageEntity
import com.fsoc.template.data.db.entity.UserEntity

@Database(
    entities = [UserEntity::class, CustomerEntity::class, ListMessageEntity::class, MessageEntity::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "mydatabase"
    }

    abstract fun userDao(): UserDao
    abstract fun customerDao(): CustomerDao
    abstract fun listMessageDao(): ListMessageDao
    abstract fun messageDao(): MessageDao
}