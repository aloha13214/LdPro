package com.fsoc.template.data.db.helper.message.detail

import com.fsoc.template.data.db.AppDatabase
import com.fsoc.template.data.db.entity.MessageEntity
import javax.inject.Inject

class MessagesDatabaseImplement @Inject constructor() : MessageDatabase {
    @Inject
    lateinit var database: AppDatabase
    override suspend fun insertMessages(listMessageEntity: MessageEntity) {
        database.messageDao().insertMessages(listMessageEntity)
    }

    override suspend fun getAllMessage(subId: Int): List<MessageEntity> {
        return database.messageDao().getAllMessage(subId)
    }

    override suspend fun deleteMessage(messagesEntity: List<MessageEntity>) {
        database.messageDao().delete(messagesEntity)
    }

    override suspend fun updateMessage(messageEntity: MessageEntity) {
        database.messageDao().update(messageEntity)
    }
}