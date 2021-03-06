package com.fsoc.template.domain.repository

import com.fsoc.template.data.api.entity.Todo
import com.fsoc.template.domain.entity.BaseModel
import io.reactivex.Single

interface BaseRepo {
    fun checkAppExpire(): Single<BaseModel>
    fun checkMaintenanceMode(): Single<BaseModel>
    fun fetchTodo(): List<Todo>
}