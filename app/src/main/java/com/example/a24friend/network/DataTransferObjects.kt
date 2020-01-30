package com.example.a24friend.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class UserApi(
    @field:Json(name = "user_doc_id")
    val userId: String)

@JsonClass(generateAdapter = true)
data class ChatRoomApi(
    @field:Json(name = "id")
    val chatRoomId: String,
    @field:Json(name = "first_user_doc_id")
    val userId: String,
    @field:Json(name = "last_user_doc_id")
    val matchUserId: String,
    @field:Json(name = "city_doc_id")
    val cityId: String,
    val languages: List<String>,
    @field:Json(name ="started_at" )
    val startedAt: Date,
    @field:Json(name = "ended_at")
    val endedAt: Date)

@JsonClass(generateAdapter = true)
data class CitysApi(
    @field:Json(name = "city")
    val cityList: List<CityApi>)

@JsonClass(generateAdapter = true)
data class CityApi(
    @field:Json(name = "doc_id")
    val id: String,
    val name: String)

@JsonClass(generateAdapter = true)
data class LanguagesApi(
    @field:Json(name = "language")
    val languageList: List<LanguageApi>)

@JsonClass(generateAdapter = true)
data class LanguageApi(
    @field:Json(name = "doc_id")
    val id: String,
    val name: String)