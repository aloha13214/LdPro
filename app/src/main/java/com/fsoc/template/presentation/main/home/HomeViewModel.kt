package com.fsoc.template.presentation.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fsoc.template.common.Resource
import com.fsoc.template.data.api.ApiHelper
import com.fsoc.template.data.api.entity.Todo
import com.fsoc.template.data.db.DatabaseHelper
import com.fsoc.template.data.db.entity.UserEntity
import com.fsoc.template.data.db.helper.message.detail.MessageDatabase
import com.fsoc.template.data.db.helper.message.list.MessagesDatabaseHelper
import com.fsoc.template.presentation.base.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class HomeViewModel @Inject constructor(
    private val apiHelper: ApiHelper,
    private val databaseHelper: DatabaseHelper,
    val databaseHelperMessage: MessagesDatabaseHelper,
    val database: MessageDatabase
) : BaseViewModel() {

    private val listTodo = MutableLiveData<Resource<List<Todo>>>()
    private val usersFromDatabase = MutableLiveData<Resource<List<UserEntity>>>()

    private val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        listTodo.postValue(Resource.error(exception.fillInStackTrace(), null))
    }

    private val coroutineContext: CoroutineContext = exceptionHandler + Dispatchers.IO

    fun fetch() {
        viewModelScope.launch(coroutineContext) {
            listTodo.postValue(Resource.loading(null))
            val todos = apiHelper.getTodos()
            listTodo.postValue(Resource.success(todos))
        }
    }

    fun getAllUserFromDatabase() {
        viewModelScope.launch {
            usersFromDatabase.postValue(Resource.loading(null))
            try {
                val users = databaseHelper.getUsers()
                usersFromDatabase.postValue(Resource.success(users))
            } catch (ex: Exception) {
                usersFromDatabase.postValue(Resource.error(ex.fillInStackTrace(), null))
            }
        }
    }

    fun getTodos(): LiveData<Resource<List<Todo>>> = listTodo
    fun getUsersFromDatabase(): LiveData<Resource<List<UserEntity>>> = usersFromDatabase
}