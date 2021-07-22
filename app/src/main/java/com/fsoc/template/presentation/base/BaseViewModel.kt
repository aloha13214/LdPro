package com.fsoc.template.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fsoc.template.data.api.BaseApi
import com.fsoc.template.domain.entity.BaseModel
import com.fsoc.template.domain.usecase.BaseUseCase
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.Exception
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    lateinit var baseUseCase: BaseUseCase

    @Inject
    lateinit var baseApi: BaseApi

    @Inject
    lateinit var gson: Gson

    private val mDisposables = CompositeDisposable()

    val mError = MutableLiveData<Throwable>()

    val checkAppExpireLiveData = MutableLiveData<BaseModel>()
    val checkMaintenanceModeLiveData = MutableLiveData<BaseModel>()

    protected fun Disposable.track() {
        mDisposables.add(this)
    }

    override fun onCleared() {
        mDisposables.clear()
        super.onCleared()
    }

    fun <T> objectToString(value: T): String? {
        return try {
            gson.toJson(value)
        }catch (ex: Exception){
            null
        }
    }

    inline fun <reified T> stringToObject(value: String): T? {
        return try {
            gson.fromJson(value, T::class.java)
        }catch (ex: Exception){
            null
        }
    }

    fun unregisterFirebase() {
//        Single.just {
//            FireBaseToken.unregisterToken()
//        }.applyIoScheduler()
//            .subscribe({
//                Logger.d("unregisterFirebase subscribe success")
//            }, { error ->
//                Logger.d("unregisterFirebase subscribe error")
//            })
//            .track()

    }
}