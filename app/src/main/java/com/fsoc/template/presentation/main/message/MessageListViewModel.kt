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
import java.text.ParsePosition
import javax.inject.Inject

class MessageListViewModel @Inject constructor(
    private val apiHelper: ApiHelper,
    val databaseHelper: MessageDatabaseHelper
) : BaseViewModel() {

    private var _message = MutableLiveData<Resource<List<ListMessageEntity>>>()
    private var _isDelete = MutableLiveData<Resource<Unit>>()

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

    fun removeMessage(): LiveData<Resource<Unit>> = _isDelete

    fun removeListMessage(position: Int) {
        val listMessageEntity = _message.value?.data?.get(position)
        viewModelScope.launch(Dispatchers.IO) {
            _isDelete.postValue(Resource.loading(null))
            try {
                val result = listMessageEntity?.let { databaseHelper.deleteListMessage(it) }
                _isDelete.postValue(Resource.success(result))
            } catch (ex: Exception) {
                _isDelete.postValue(Resource.error(ex.fillInStackTrace(), null))
            }
        }
    }

}