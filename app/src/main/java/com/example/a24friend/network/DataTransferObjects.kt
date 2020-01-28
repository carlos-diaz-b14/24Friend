package com.example.a24friend.network

import com.example.a24friend.database.MessageEntity
import com.example.a24friend.domain.Message
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class MessageList(
    @field:Json(name="results")
    val messageList: List<ContactApi>)

@JsonClass(generateAdapter = true)
data class ContactApi(val id: String,
                      val postMessage: String,
                      val time: Date,
                      val fromUserID: String,
                      val toUserID: String,
                      val status: String)

fun MessageList.asDatabaseModel(): List<MessageEntity> {
    return messageList.map {
        MessageEntity(
            id = it.id,
            postMessage = it.postMessage,
            time = it.time,
            fromUserID = it.fromUserID,
            toUserID = it.toUserID,
            status = it.status
        )
    }
}

fun MessageList.asDomainModel(): List<Message> {
    return messageList.map {
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