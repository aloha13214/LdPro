package com.fsoc.template.data.db.helper.message

import com.fsoc.template.data.db.entity.ListMessageEntity

interface MessageDatabaseHelper {
    suspend fun insertMessages(listMessageEntity: ListMessageEntity)
    suspend fun getAllListMessage(): List<ListMessageEntity>
    suspend fun getListMessageById(listMessageId: Long): ListMessageEntity
    suspend fun deleteListMessage(listMessageEntity: ListMessageEntity)
    suspend fun updateListMessage(listMessageEntity: ListMessageEntity)
}