package com.example.a24friend.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * A retrofit service to fetch a contact list.
 */
interface MessageApiService {
    @GET("")
    fun getMessageAsync(@Query("results") num: Int): Deferred<MessageList>
}

/**
 * Main entry point for network access.
 * call like `ContactNetwork.contacts.getContacts()`
 */
object MessageNetwork {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://randomuser.me/api/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val message: MessageApiService = retrofit.create(MessageApiService::class.java)
}

/**
 * To parse Json to object
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
