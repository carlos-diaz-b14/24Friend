package com.example.a24friend.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
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


    @GET("")
    fun getMessageAsync(@Query("results") num: Int): Deferred<MessageList>
}

object Network {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val api: ApiService = retrofit.create(ApiService::class.java)
}
