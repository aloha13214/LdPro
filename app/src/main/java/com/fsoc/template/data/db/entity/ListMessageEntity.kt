package com.fsoc.template.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ListMessageEntity(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "lastMessage") var lastMessage: String = "",
    @ColumnInfo(name = "isAdd") var isAdd: Boolean = false,
) {
    constructor(
        title: String,
        lastMessage: String,
        isAdd: Boolean,
    ) : this(0, title, lastMessage, isAdd)
}