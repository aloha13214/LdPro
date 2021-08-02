package com.fsoc.template.data.db.helper.message.detail

import com.fsoc.template.data.db.entity.MessageEntity

interface ChatDatabaseHelper{
    suspend fun insertMessages(listMessageEntity: MessageEntity)
    suspend fun getAllMessage(subId: Int): List<MessageEntity>
    suspend fun deleteMessage(messagesEntity: List<MessageEntity>)
    suspend fun updateMessage(messageEntity: MessageEntity)
}