package com.fsoc.template.data.db.helper.message.list

import com.fsoc.template.data.db.AppDatabase
import com.fsoc.template.data.db.entity.ListMessageEntity
import javax.inject.Inject

class MessagesDatabaseHelperImplement @Inject constructor() : MessagesDatabaseHelper {
    @Inject
    lateinit var database: AppDatabase
    override suspend fun insertMessages(listMessageEntity: ListMessageEntity) {
        database.listMessageDao().insertMessages(listMessageEntity)
    }

    override suspend fun getAllListMessage(): List<ListMessageEntity> {
        return database.listMessageDao().getAllListMessage()
    }

    override suspend fun getListMessageById(id: Int): ListMessageEntity {
        return database.listMessageDao().getMessageById(id)
    }

    override suspend fun deleteListMessage(listMessageEntity: ListMessageEntity) {
        database.listMessageDao().delete(listMessageEntity)
    }

    override suspend fun updateListMessage(listMessageEntity: ListMessageEntity) {
        database.listMessageDao().update(listMessageEntity)
    }
}