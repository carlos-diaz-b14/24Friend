package com.example.a24friend.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.a24friend.database.MessageDatabase
import com.example.a24friend.database.asDomainModel
import com.example.a24friend.domain.Message
import com.example.a24friend.network.MessageNetwork
import com.example.a24friend.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class MessageRepository(private val database: MessageDatabase) {

    val message: LiveData<List<Message>> = Transformations.map(database.messageDao.getAllContacts()) {
        it.asDomainModel()
    }

    suspend fun refreshMessage() {
        withContext(Dispatchers.IO) {
            Timber.d("refreshContacts() called")
            database.messageDao.clear()
            val message = MessageNetwork.message.getMessageAsync(20).await()
            database.messageDao.insertAll(message.asDatabaseModel())
        }
    }

    suspend fun clearContacts() {
        withContext(Dispatchers.IO) {
            database.messageDao.clear()
        }
    }
}