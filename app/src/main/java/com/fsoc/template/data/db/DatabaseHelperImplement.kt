package com.fsoc.template.data.db

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
}