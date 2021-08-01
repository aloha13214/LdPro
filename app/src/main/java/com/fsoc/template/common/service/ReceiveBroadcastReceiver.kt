package com.fsoc.template.common.service

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.fsoc.template.R
import com.fsoc.template.data.db.entity.ListMessageEntity
import com.fsoc.template.data.db.entity.MessageEntity
import com.fsoc.template.data.db.helper.message.detail.MessageDatabase
import com.fsoc.template.data.db.helper.message.list.MessagesDatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*


/**
 * Receive Broadcast Receiver.
 */
class ReceiveBroadcastReceiver(
    val databaseHelper: MessagesDatabaseHelper,
    val database: MessageDatabase,
    private val callback: (ListMessageEntity) -> Unit
) : BroadcastReceiver() {

    @SuppressLint("HardwareIds", "SimpleDateFormat")
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title")
        val text = intent.getStringExtra("text")
        val id = intent.getIntExtra("id", 0)
        if (text != null) {
            if (isCheck(context, text)) {
                main(
                    ListMessageEntity(
                        id,
                        title?.replace(getTextReplace(context, title), "") ?: "",
                        text,
                        false,
                        Calendar.getInstance().time.time
                    )
                )
            }
        }
    }

    private fun isCheck(context: Context, text: String): Boolean {
        val lst = context.resources.getStringArray(R.array.message).toCollection(arrayListOf())
        var isCheck = true
        lst.forEach {
            if (text.contains(it)) {
                isCheck = false
                return@forEach
            }
        }
        return isCheck
    }

    private fun getTextReplace(context: Context, title: String?): String {
        val tmp = context.resources.getStringArray(R.array.tin_nhan).toCollection(arrayListOf())
        var replace = ""
        tmp.forEach {
            if (title?.contains(it) == true) {
                replace = it
            }
        }
        return replace
    }

    fun main(listMessageEntity: ListMessageEntity) = runBlocking<Unit> {
        launch(Dispatchers.IO) {
            val value = databaseHelper.getAllListMessage()
            if (value.any { listMessageEntity.id == it.id }) {
                databaseHelper.insertMessages(listMessageEntity.apply {
                    title = value.firstOrNull { listMessageEntity.id == it.id }?.title
                        ?: ""
                })
            } else {
                databaseHelper.insertMessages(listMessageEntity)
            }

            database.insertMessages(
                MessageEntity(
                    subId = listMessageEntity.id,
                    content = listMessageEntity.lastMessage,
                    isUser = false,
                    time = Calendar.getInstance().timeInMillis
                )
            )
            callback.invoke(listMessageEntity)
        }
    }
}