package com.example.a24friend.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity (
    @PrimaryKey
    val userId: String,
    var token: String?,
    var city: String?,
    var nickname: String?,
    var language: String?
)
