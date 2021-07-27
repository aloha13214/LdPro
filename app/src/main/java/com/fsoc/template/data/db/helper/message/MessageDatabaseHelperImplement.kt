package com.fsoc.template.data.db.helper.message

import com.fsoc.template.data.db.AppDatabase
import com.fsoc.template.data.db.entity.ListMessageEntity
import javax.inject.Inject

class MessageDatabaseHelperImplement @Inject constructor() : MessageDatabaseHelper {
    @Inject
    lateinit var database: AppDatabase
    override suspend fun insertMessages(listMessageEntity: ListMessageEntity) {
        database.listMessageDao().insertMessages(listMessageEntity)
    }

    override suspend fun getAllListMessage(): List<ListMessageEntity> {
        return database.listMessageDao().getAllListMessage()
    }

    override suspend fun getListMessageById(title: String): ListMessageEntity {
        return database.listMessageDao().getMessageById(title)
    }

    override suspend fun deleteListMessage(listMessageEntity: ListMessageEntity) {
        database.listMessageDao().delete(listMessageEntity)
    }

    override suspend fun updateListMessage(listMessageEntity: ListMessageEntity) {
        database.listMessageDao().update(listMessageEntity)
    }
}