package com.fsoc.template.data.api

import com.fsoc.template.data.api.entity.Todo
import javax.inject.Inject

class ApiHelperImplement @Inject constructor(): ApiHelper {
    @Inject
    lateinit var apiService: ApiService

    override suspend fun getTodos(): List<Todo> {
        return apiService.getTodos()
    }
}