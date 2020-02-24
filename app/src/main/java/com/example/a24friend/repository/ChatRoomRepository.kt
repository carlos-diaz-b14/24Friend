package com.example.a24friend.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.a24friend.database.ChatRoomEntity
import com.example.a24friend.database.asDomainModel
import com.example.a24friend.domain.ChatRoom
import com.example.a24friend.database.MessageDatabase
import com.example.a24friend.domain.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChatRoomRepository(private val database: MessageDatabase) {

    fun getChatRoom(userID: String): ChatRoom {
        return database.chatRoomDao.getChatRoom(userID).asDomainModel()
    }
    suspend fun clearChatRoom() {
        withContext(Dispatchers.IO) {
            database.chatRoomDao.clear()
        }
    }
}