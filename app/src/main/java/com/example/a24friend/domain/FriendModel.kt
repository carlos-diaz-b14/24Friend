package com.example.a24friend.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Friend(val id: String,
                   val name: String,
                   val location: String,
                   val language: String) : Parcelable
