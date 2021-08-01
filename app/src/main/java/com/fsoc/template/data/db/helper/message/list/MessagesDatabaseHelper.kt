package com.fsoc.template.data.db.helper.message.list

import com.fsoc.template.data.db.entity.ListMessageEntity

interface MessagesDatabaseHelper {
    suspend fun insertMessages(listMessageEntity: ListMessageEntity)
    suspend fun getAllListMessage(): List<ListMessageEntity>
    suspend fun getListMessageById(id: Int): ListMessageEntity
    suspend fun deleteListMessage(listMessageEntity: ListMessageEntity)
    suspend fun updateListMessage(listMessageEntity: ListMessageEntity)
}