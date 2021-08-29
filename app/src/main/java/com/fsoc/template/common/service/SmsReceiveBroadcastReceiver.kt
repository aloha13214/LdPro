package com.fsoc.template.common.service

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Telephony
import androidx.annotation.RequiresApi
import com.fsoc.template.common.extension.isCheck
import com.fsoc.template.data.db.entity.ListMessageEntity
import com.fsoc.template.data.db.entity.MessageEntity
import com.fsoc.template.data.db.entity.TypeMessage
import com.fsoc.template.data.db.helper.message.detail.ChatDatabaseHelper
import com.fsoc.template.data.db.helper.message.list.MessagesDatabaseHelper
import com.fsoc.template.domain.entity.PhoneNumber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.collections.ArrayList

/**
 * Sms Receive Broadcast Receiver.
 */
class SmsReceiveBroadcastReceiver(
    val phoneNumbers: ArrayList<PhoneNumber>,
    val databaseHelper: MessagesDatabaseHelper,
    val chatDatabaseHelper: ChatDatabaseHelper,
    private val callback: (ListMessageEntity) -> Unit,
    private val callbackMessage: (MessageEntity) -> Unit
) : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("HardwareIds", "SimpleDateFormat")
    override fun onReceive(context: Context, intent: Intent) {
            for (sms in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                val contact = phoneNumbers.firstOrNull {
                    it.phoneNumber == sms.originatingAddress
                }
                val title = sms.originatingAddress
                val content = sms.displayMessageBody

                if (isCheck(context, content)) {
                    main(
                        ListMessageEntity(
                            title ?: "",
                            contact?.name ?: title ?: "",
                            content,
                            false,
                            contact?.phoneNumber ?: title ?: "",
                            TypeMessage.TYPE_SMS.value,
                            sms.timestampMillis
                        )
                    )
                }
            }
    }
    fun main(listMessageEntity: ListMessageEntity) = runBlocking<Unit> {
        launch(Dispatchers.IO) {
            val value = databaseHelper.getAllListMessage()
            val message = MessageEntity(
                subId = listMessageEntity.id,
                content = listMessageEntity.lastMessage,
                isUser = false,
                time = listMessageEntity.time
            )
            if (value.any { listMessageEntity.id == it.id }) {
                databaseHelper.insertMessages(listMessageEntity.apply {
                    title = value.firstOrNull { listMessageEntity.id == it.id }?.title
                        ?: ""
                })
            } else {
                databaseHelper.insertMessages(listMessageEntity)
            }
            chatDatabaseHelper.insertMessages(message)
            callbackMessage.invoke(message)
            callback.invoke(listMessageEntity)
        }
    }
}