package com.fsoc.template.data.db.dao

import androidx.room.*
import com.fsoc.template.data.db.entity.ListMessageEntity

@Dao
interface ListMessageDao {
    @Query("SELECT * FROM listmessageentity")
    suspend fun getAllListMessage(): List<ListMessageEntity>

    @Query("SELECT * FROM listmessageentity WHERE id = :id")
    suspend fun getMessageById(id: Int): ListMessageEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messageEntity: ListMessageEntity)

    @Delete
    suspend fun delete(messageEntity: ListMessageEntity)

    @Update
    suspend fun update(messageEntity: ListMessageEntity)
}