package com.fsoc.template.presentation.main.message.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fsoc.template.common.Resource
import com.fsoc.template.data.api.ApiHelper
import com.fsoc.template.data.db.entity.MessageEntity
import com.fsoc.template.data.db.helper.message.detail.MessageDatabase
import com.fsoc.template.presentation.base.BaseViewModel
import com.fsoc.template.presentation.main.message.list.adapter.MessageModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MessagesViewModel @Inject constructor(
    private val apiHelper: ApiHelper,
    val databaseHelper: MessageDatabase
) : BaseViewModel() {

    var modelMessage : MessageModel? = null
    private var _message = MutableLiveData<Resource<List<MessageEntity>>>()

    fun getListMessage(): LiveData<Resource<List<MessageEntity>>> = _message

    fun getAllListMessage() {
        viewModelScope.launch(Dispatchers.IO) {
            _message.postValue(Resource.loading(null))
            try {
                val result = databaseHelper.getAllMessage(modelMessage?.id ?: 0)
                _message.postValue(Resource.success(result))
            } catch (ex: Exception) {
                _message.postValue(Resource.error(ex.fillInStackTrace(), null))
            }
        }
    }
}