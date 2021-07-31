package com.fsoc.template.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ListMessageEntity(
    @PrimaryKey var title: String = "",
    @ColumnInfo(name = "lastMessage") var lastMessage: String = "",
    @ColumnInfo(name = "isAdd") var isAdd: Boolean = false,
    @ColumnInfo(name = "time") var time: Long,
)