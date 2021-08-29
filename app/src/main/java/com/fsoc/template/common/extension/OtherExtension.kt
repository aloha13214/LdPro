package com.fsoc.template.common.extension

import android.content.Context
import com.fsoc.template.R


fun isCheck(context: Context, text: String): Boolean {
    val lst = context.resources.getStringArray(R.array.message).toCollection(arrayListOf())
    var isCheck = true
    lst.forEach {
        if (text.contains(it, true)) {
            isCheck = false
            return@forEach
        }
    }
    return isCheck
}

fun isCheckTitle(context: Context, text: String): Boolean {
    val lst = context.resources.getStringArray(R.array.titleMessage).toCollection(arrayListOf())
    var isCheck = true
    lst.forEach {
        if (text.contains(it, true)) {
            isCheck = false
            return@forEach
        }
    }
    return isCheck
}

fun getTextReplace(context: Context, title: String?): String {
    val tmp = context.resources.getStringArray(R.array.tin_nhan).toCollection(arrayListOf())
    var replace = ""
    tmp.forEach {
        if (title?.contains(it) == true) {
            replace = it
        }
    }
    return replace
}