package com.fsoc.template.common.service

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.fsoc.template.data.db.entity.ListMessageEntity
import com.fsoc.template.data.db.helper.message.MessageDatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

/**
 * Receive Broadcast Receiver.
 */
class ReceiveBroadcastReceiver(
    val databaseHelper: MessageDatabaseHelper,
    private val callback: (ListMessageEntity) -> Unit
    ) : BroadcastReceiver() {

    @SuppressLint("HardwareIds", "SimpleDateFormat")
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title")
        val text = intent.getStringExtra("text")
        if (text != null) {
            if (!text.contains("new messages") &&
                !text.contains("WhatsApp Web is currently active") &&
                !text.contains("WhatsApp Web login")
            ) {
                main(
                    ListMessageEntity(
                        title ?: "",
                        text,
                        false,
                        Calendar.getInstance().time.time
                    )
                )
            }
        }
    }

    fun main(listMessageEntity: ListMessageEntity) = runBlocking<Unit> {
        launch(Dispatchers.IO) {
            val value = databaseHelper.getAllListMessage()
            if (value.any { listMessageEntity.title.contains(it.title) }) {
                databaseHelper.updateListMessage(listMessageEntity.apply {
                    title = value.firstOrNull { listMessageEntity.title.contains(it.title) }?.title
                        ?: ""
                })
            } else {
                databaseHelper.insertMessages(listMessageEntity)
            }
            callback.invoke(listMessageEntity)
        }
    }
}