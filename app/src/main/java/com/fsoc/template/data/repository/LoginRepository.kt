package com.fsoc.template.data.repository

import com.fsoc.template.data.api.BaseApi
import javax.inject.Inject

class LoginRepository @Inject constructor(): LoginRepo {
    @Inject
    lateinit var baseApi: BaseApi
}