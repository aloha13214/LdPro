package com.fsoc.template.presentation.main.message.list.adapter

import com.fsoc.template.data.db.entity.ListMessageEntity
import com.fsoc.template.domain.entity.PhoneNumber
import java.io.Serializable

data class MessageModel(
    var id: String,
    var title: String,
    var lastMessage: String,
    var isAdd: Boolean,
    var phoneNumber: String,
    var type: Int,
    var time: Long,
) : Serializable {
    companion object {
        fun convertModel(entity: ListMessageEntity?): MessageModel {
            return MessageModel(
                entity?.id ?: "",
                entity?.title ?: "",
                entity?.lastMessage ?: "",
                entity?.isAdd ?: false,
                entity?.phoneNumber ?: "",
                entity?.type ?: 0,
                entity?.time ?: 0
            )
        }

        fun convertEntity(model: MessageModel?): ListMessageEntity {
            return ListMessageEntity(
                model?.id ?: "",
                model?.title ?: "",
                model?.lastMessage ?: "",
                model?.isAdd ?: false,
                model?.phoneNumber ?: "",
                model?.type ?: 0,
                model?.time ?: 0
            )
        }
    }
}