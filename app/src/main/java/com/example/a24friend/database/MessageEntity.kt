package com.example.a24friend.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.a24friend.domain.Message
import java.util.*

@Entity(tableName = "message")
data class MessageEntity constructor(
    @PrimaryKey
    val id: String = "",
    var postMessage: String,
    val fromUserID: String,
    val chatRoomID: String,
    var time: Int)

fun List<MessageEntity>.asDomainModel(): List<Message> {
    return map {
        Message(
            id = it.id,
            postMessage = it.postMessage,
            fromUserID = it.fromUserID,
            chatRoomID = it.chatRoomID,
            time = it.time
        )
    }
}