package com.fsoc.template.common

data class Resource<out T>(val status: Status, val data: T?, val e: Throwable?) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(e: Throwable, data: T?): Resource<T> {
            return Resource(Status.ERROR, null, e)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

    }

}