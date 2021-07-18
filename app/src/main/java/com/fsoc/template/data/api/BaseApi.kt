package com.fsoc.template.data.api

import com.fsoc.template.data.api.entity.BaseDto
import com.fsoc.template.data.api.entity.Todo
import com.fsoc.template.domain.entity.BaseModel
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET

interface BaseApi {
    @GET(ApiConfig.CHECK_APP_EXPIRE)
    fun getCheckAppExpire(): Single<BaseDto>

    @GET(ApiConfig.CHECK_MAINTENANCE_MODE)
    fun checkMaintenanceMode(): Single<BaseDto>

    @GET(ApiConfig.GET_TODO)
    fun getTodos(): Call<Todo>
}