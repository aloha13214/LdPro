package com.fsoc.template.data.api

import com.fsoc.template.data.api.entity.Todo

interface ApiHelper {
    suspend fun getTodos(): List<Todo>
}