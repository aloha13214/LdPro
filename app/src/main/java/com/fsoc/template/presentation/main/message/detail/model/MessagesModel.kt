package com.fsoc.template.presentation.main.message.detail.model

import com.fsoc.template.data.db.entity.MessageEntity
import java.io.Serializable

data class MessagesModel(
    var id: Int,
    var subId: Int,
    var content: String,
    var isUser: Boolean,
    var time: Long,
) : Serializable {
    companion object {
        fun convertModel(entity: MessageEntity?): MessagesModel {
            return MessagesModel(
                entity?.id ?: 0,
                entity?.subId ?: 0,
                entity?.content ?: "",
                entity?.isUser ?: false,
                entity?.time ?: 0
            )
        }

        fun convertEntity(model: MessagesModel?): MessageEntity {
            return MessageEntity(
                model?.id ?: 0,
                model?.subId ?: 0,
                model?.content ?: "",
                model?.isUser ?: false,
                model?.time ?: 0
            )
        }
    }
}