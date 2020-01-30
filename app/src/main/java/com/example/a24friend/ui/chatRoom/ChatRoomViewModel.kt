package com.example.a24friend.ui.chatRoom

import android.app.Application
import androidx.lifecycle.*
import com.example.a24friend.database.getDatabase
import com.example.a24friend.domain.Message
import com.example.a24friend.repository.MessageRepository
import com.example.a24friend.util.ApiStatus
import kotlinx.coroutines.*

class ChatRoomViewModel(application: Application) : AndroidViewModel(application) {

    private val messageRepository: MessageRepository = MessageRepository(getDatabase(application))

    val message: LiveData<List<Message>> = messageRepository.message

    private val viewModelJob = Job()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun clearDataFromRepository() = viewModelScope.launch {
        messageRepository.clearMessage()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(val app: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ChatRoomViewModel::class.java)) {
                return ChatRoomViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}