package com.fsoc.template.presentation.main.message

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fsoc.template.common.Resource
import com.fsoc.template.data.api.ApiHelper
import com.fsoc.template.data.db.entity.ListMessageEntity
import com.fsoc.template.data.db.helper.message.MessageDatabaseHelper
import com.fsoc.template.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MessageListViewModel @Inject constructor(
    private val apiHelper: ApiHelper,
    val databaseHelper: MessageDatabaseHelper
) : BaseViewModel() {

    private var _message = MutableLiveData<Resource<List<ListMessageEntity>>>()

    fun getListMessage(): LiveData<Resource<List<ListMessageEntity>>> = _message

    fun getAllListMessage() {
        viewModelScope.launch(Dispatchers.IO) {
            _message.postValue(Resource.loading(null))
            try {
                val result = databaseHelper.getAllListMessage()
                _message.postValue(Resource.success(result))
            } catch (ex: Exception) {
                _message.postValue(Resource.error(ex.fillInStackTrace(), null))
            }
        }
    }

}