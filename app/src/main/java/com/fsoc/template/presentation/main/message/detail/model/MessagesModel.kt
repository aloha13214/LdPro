package com.fsoc.template.presentation.main.message.detail.model

import com.fsoc.template.data.db.entity.MessageEntity
import java.io.Serializable

data class MessagesModel(
    var subId: Int,
    var content: String,
    var isUser: Boolean,
    var time: Long,
) : Serializable {
    companion object {
        fun convertModel(entity: MessageEntity?): MessagesModel {
            return MessagesModel(
                entity?.subId ?: 0,
                entity?.content ?: "",
                entity?.isUser ?: false,
                entity?.time ?: 0
            )
        }

        fun convertEntity(model: MessagesModel?): MessageEntity {
            return MessageEntity(
                model?.time ?: 0,
                model?.subId ?: 0,
                model?.content ?: "",
                model?.isUser ?: false,
            )
        }
    }
}