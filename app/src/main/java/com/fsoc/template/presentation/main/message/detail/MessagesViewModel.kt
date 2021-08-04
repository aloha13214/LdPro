package com.fsoc.template.presentation.main.message.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fsoc.template.common.Resource
import com.fsoc.template.data.api.ApiHelper
import com.fsoc.template.data.db.DatabaseHelper
import com.fsoc.template.data.db.entity.MessageEntity
import com.fsoc.template.data.db.helper.message.detail.ChatDatabaseHelper
import com.fsoc.template.data.db.helper.message.list.MessagesDatabaseHelper
import com.fsoc.template.presentation.base.BaseViewModel
import com.fsoc.template.presentation.main.message.list.adapter.MessageModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MessagesViewModel @Inject constructor(
    private val apiHelper: ApiHelper,
    val databaseHelper: MessagesDatabaseHelper,
    val chatDatabaseHelper: ChatDatabaseHelper
) : BaseViewModel() {

    var modelMessage : MessageModel? = null
    private var _message = MutableLiveData<Resource<ArrayList<MessageEntity>>>()

    fun getListMessage(): LiveData<Resource<ArrayList<MessageEntity>>> = _message

    fun getAllListMessage() {
        viewModelScope.launch(Dispatchers.IO) {
            _message.postValue(Resource.loading(null))
            try {
                val result = chatDatabaseHelper.getAllMessage(modelMessage?.id ?: 0)
                _message.postValue(Resource.success(result.toCollection(arrayListOf())))
            } catch (ex: Exception) {
                _message.postValue(Resource.error(ex.fillInStackTrace(), null))
            }
        }
    }
}