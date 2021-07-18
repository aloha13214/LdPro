package com.fsoc.template.presentation.main.login

import androidx.lifecycle.viewModelScope
import com.fsoc.template.presentation.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(): BaseViewModel() {
    fun fetchUser() {
        baseApi.getTodos()
    }
}