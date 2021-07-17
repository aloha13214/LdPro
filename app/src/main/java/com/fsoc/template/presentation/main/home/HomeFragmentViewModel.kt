package com.fsoc.template.presentation.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fsoc.template.domain.repository.BaseRepo
import com.fsoc.template.presentation.base.BaseViewModel
import javax.inject.Inject

class HomeFragmentViewModel @Inject constructor(private val baseRepo: BaseRepo): BaseViewModel() {

    private val _listUser = MutableLiveData<String>()
    var listUser: LiveData<String> = _listUser

    fun fetch() {
        _listUser.value = "Dungnt"
    }
}