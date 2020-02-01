package com.example.a24friend.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class ChatRoom(val id: String,
                    val userID: String,
                    val matchUserID: String,
                    val city: String,
                    val language: List<String>,
                    val startedAt: Date) : Parcelable