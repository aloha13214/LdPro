package com.fsoc.template.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ListMessageEntity(
    @PrimaryKey var id: String,
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "lastMessage") var lastMessage: String = "",
    @ColumnInfo(name = "isAdd") var isAdd: Boolean = false,
    @ColumnInfo(name = "phoneNumber") var phoneNumber: String = "",
    @ColumnInfo(name = "type") var type: Int = 0,
    @ColumnInfo(name = "time") var time: Long,
)

enum class TypeMessage(val value: Int){
    TYPE_ZALO(0),
    TYPE_SMS(1)
}