package com.example.a24friend.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Message(val id: String,
                   var message: String,
                   var userDocId: String,
                   var roomDocId: String,
                   var createdAt: String) : Parcelable