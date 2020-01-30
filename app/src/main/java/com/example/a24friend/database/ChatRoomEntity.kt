package com.example.a24friend.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.a24friend.domain.ChatRoom
import java.util.*

@Entity(tableName = "room")
data class ChatRoomEntity constructor(
    @PrimaryKey
    val id: String = "",
    var userID: String,
    var matchUserID: String,
    val city: String,
    val language: List<String>,
    var startedAt: Date)

fun ChatRoomEntity.asDomainModel(): ChatRoom {
    return ChatRoom(
        id = id,
        userID = userID,
        matchUserID = matchUserID,
        city = city,
        language = language,
        startedAt = startedAt
        )
}