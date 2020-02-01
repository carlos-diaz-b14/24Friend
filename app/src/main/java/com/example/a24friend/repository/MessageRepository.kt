package com.example.a24friend.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.a24friend.database.MessageDatabase
import com.example.a24friend.database.asDomainModel
import com.example.a24friend.domain.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MessageRepository(private val database: MessageDatabase) {

    val message: LiveData<List<Message>> = Transformations.map(database.messageDao.getAllMessages()) {
        it.asDomainModel()
    }

    suspend fun clearMessage() {
        withContext(Dispatchers.IO) {
            database.messageDao.clear()
        }
    }
}