package com.example.a24friend.network

import com.example.a24friend.domain.ChatRoom
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

const val BASE_URL = "https://asia-northeast1-bubbly-trail-265822.cloudfunctions.net/"

interface ApiService {
    @POST("get_user")
    fun getUserIdAsync(@Field("user_doc_id") id: String): Deferred<String>

    @POST("match_room")
    fun matchRoomAsync(@Field("user_doc_id") user_id: String,
                       @Field("city_doc_id") city_id: String,
                       @Field("languages") languages: List<String>)
            : Deferred<ChatRoom>
}

object Network {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val api: ApiService = retrofit.create(ApiService::class.java)
}

/**
 * To parse Json to object
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
