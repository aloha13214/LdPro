package com.fsoc.template.data.db

import com.fsoc.template.data.db.entity.UserEntity

interface DatabaseHelper {
    suspend fun getUsers(): List<UserEntity>

    suspend fun insertUser(todos: UserEntity)
}