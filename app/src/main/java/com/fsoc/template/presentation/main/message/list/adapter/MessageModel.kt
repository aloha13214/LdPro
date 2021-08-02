package com.fsoc.template.presentation.main.message.list.adapter

import com.fsoc.template.data.db.entity.ListMessageEntity
import java.io.Serializable

data class MessageModel(
    var id: Int,
    var title: String,
    var lastMessage: String,
    var isAdd: Boolean,
    var time: Long,
) : Serializable {
    companion object {
        fun convertModel(entity: ListMessageEntity?): MessageModel {
            return MessageModel(
                entity?.id ?: 0,
                entity?.title ?: "",
                entity?.lastMessage ?: "",
                entity?.isAdd ?: false,
                entity?.time ?: 0
            )
        }

        fun convertEntity(model: MessageModel?): ListMessageEntity {
            return ListMessageEntity(
                model?.id ?: 0,
                model?.title ?: "",
                model?.lastMessage ?: "",
                model?.isAdd ?: false,
                model?.time ?: 0
            )
        }
    }
}