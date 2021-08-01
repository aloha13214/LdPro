package com.fsoc.template.data.db.dao

import androidx.room.*
import com.fsoc.template.data.db.entity.ListMessageEntity
import com.fsoc.template.data.db.entity.MessageEntity

@Dao
interface MessageDao {
    @Query("SELECT * FROM messageentity WHERE subId = :subId")
    suspend fun getAllMessage(subId: Int): List<MessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messageEntity: MessageEntity)

    @Delete
    suspend fun delete(messagesEntity: List<MessageEntity>)

    @Update
    suspend fun update(messageEntity: MessageEntity)
}