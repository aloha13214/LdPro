package com.fsoc.template.data.api

import com.fsoc.template.data.api.entity.Todo
import retrofit2.http.GET

interface ApiService {
    @GET(ApiConfig.GET_TODO)
    suspend fun getTodos(): List<Todo>
}