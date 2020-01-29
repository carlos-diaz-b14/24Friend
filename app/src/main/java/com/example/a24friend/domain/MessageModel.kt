package com.example.a24friend.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Message(val id: String,
                   var postMessage: String,
                   var time: Int,
                   var fromUserID: String,
                   var toUserID: String,
                   var status: String?) : Parcelable