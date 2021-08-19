package com.fsoc.template.presentation.main.message.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fsoc.template.common.Resource
import com.fsoc.template.data.api.ApiHelper
import com.fsoc.template.data.db.entity.MessageEntity
import com.fsoc.template.data.db.helper.message.detail.ChatDatabaseHelper
import com.fsoc.template.data.db.helper.message.list.MessagesDatabaseHelper
import com.fsoc.template.presentation.base.BaseViewModel
import com.fsoc.template.presentation.main.message.list.adapter.MessageModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MessagesViewModel @Inject constructor(
    private val apiHelper: ApiHelper,
    val databaseHelper: MessagesDatabaseHelper,
    val chatDatabaseHelper: ChatDatabaseHelper
) : BaseViewModel() {

    private var _isInsert = MutableLiveData<Resource<Unit>>()
    val insertMessage: LiveData<Resource<Unit>> = _isInsert

    var modelMessage: MessageModel? = null
    private var _message = MutableLiveData<Resource<ArrayList<MessageEntity>>>()

    fun getListMessage(): LiveData<Resource<ArrayList<MessageEntity>>> = _message

    fun getAllListMessage() {
        viewModelScope.launch(Dispatchers.IO) {
            _message.postValue(Resource.loading(null))
            try {
                val result = chatDatabaseHelper.getAllMessage(modelMessage?.id ?: "")
                _message.postValue(Resource.success(result.toCollection(arrayListOf())))
            } catch (ex: Exception) {
                _message.postValue(Resource.error(ex.fillInStackTrace(), null))
            }
        }
    }

    fun insertMessage(content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isInsert.postValue(Resource.loading(null))
            try {
                val result = chatDatabaseHelper.insertMessages(
                    MessageEntity(
                        time = Calendar.getInstance().timeInMillis,
                        subId = modelMessage?.id ?: "",
                        content = content,
                        isUser = true
                    )
                )
                databaseHelper.updateListMessage(MessageModel.convertEntity(modelMessage.apply {
                    this?.lastMessage = content
                    this?.time = Calendar.getInstance().timeInMillis
                }))
                _isInsert.postValue(Resource.success(result))
            } catch (ex: Exception) {
                _isInsert.postValue(Resource.error(ex.fillInStackTrace(), null))
            }
        }
    }
}