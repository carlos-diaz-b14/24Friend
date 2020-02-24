package com.example.a24friend.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.a24friend.domain.Message

@Entity(tableName = "message")
data class MessageEntity constructor(
    @PrimaryKey
    val id: String = "",
    var message: String,
    val userDocId: String,
    val roomDocId: String,
    var createdAt: String)

fun List<MessageEntity>.asDomainModel(): List<Message> {
    return map {
        Message(
            id = it.id,
            message = it.message,
            userDocId = it.userDocId,
            roomDocId = it.roomDocId,
            createdAt = it.createdAt
        )
    }
}