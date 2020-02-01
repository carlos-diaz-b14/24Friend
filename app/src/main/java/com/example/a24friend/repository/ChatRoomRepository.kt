package com.example.a24friend.repository

import com.example.a24friend.database.asDomainModel
import com.example.a24friend.domain.ChatRoom
import com.example.a24friend.database.MessageDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChatRoomRepository(private val database: MessageDatabase) {

    fun getChatRoom(userID: String): ChatRoom {
        val chatRoom: ChatRoom = database.chatRoomDao.getChatRoom(userID).asDomainModel()
        return chatRoom
    }
    suspend fun clearChatRoom() {
        withContext(Dispatchers.IO) {
            database.chatRoomDao.clear()
        }
    }
}