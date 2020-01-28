package com.example.a24friend.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.a24friend.domain.Friend

@Entity(tableName = "contacts")
data class FriendEntity constructor(
    @PrimaryKey
    val id: String = "",
    var name: String,
    var location: String,
    var language: String)

fun List<FriendEntity>.asDomainModel(): List<Friend> {
    return map {
        Friend(
            id = it.id,
            name = it.name,
            location = it.location,
            language = it.language
        )
    }
}