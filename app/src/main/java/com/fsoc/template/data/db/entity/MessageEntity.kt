package com.fsoc.template.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MessageEntity(
    @PrimaryKey var time: Long,
    @ColumnInfo(name = "subId") var subId: Int = 0,
    @ColumnInfo(name = "content") var content: String = "",
    @ColumnInfo(name = "isUser") var isUser: Boolean = false,
)