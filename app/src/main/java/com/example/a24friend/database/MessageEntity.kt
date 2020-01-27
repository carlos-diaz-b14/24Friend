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
    var time: Date,
    var fromUserID: String,
    var toUserID: String,
    var status: String?)

fun List<MessageEntity>.asDomainModel(): List<Message> {
    return map {
        Message(
            id = it.id,
            postMessage = it.postMessage,
            time = it.time,
            fromUserID = it.fromUserID,
            toUserID = it.toUserID,
            status = it.status
        )
    }
}