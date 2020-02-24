package com.example.a24friend.ui.chatRoom

import android.app.Application
import androidx.lifecycle.*
import com.example.a24friend.database.MessageEntity
import com.example.a24friend.database.getDatabase
import com.example.a24friend.domain.ChatRoom
import com.example.a24friend.domain.Message
import com.example.a24friend.repository.ChatRoomRepository
import com.example.a24friend.repository.MessageRepository
import com.example.a24friend.network.postChat
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import java.util.*

class ChatRoomViewModel(application: Application,val userId: String) : AndroidViewModel(application) {

    private val messageRepository: MessageRepository = MessageRepository(getDatabase(application))
    val messages: LiveData<List<Message>> = messageRepository.message


//    private val chatRoomRepository: ChatRoomRepository = ChatRoomRepository(getDatabase(application))
//    var room = chatRoomRepository.getChatRoom(userId)
    val messageText = MutableLiveData<String>()

    private val viewModelJob = Job()

    private val viewModelScope = CoroutineScope(viewModelJob + Main)

    fun clearDataFromRepository() = viewModelScope.launch {
        messageRepository.clearMessage()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(val app: Application, val userId: String) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ChatRoomViewModel::class.java)) {
                return ChatRoomViewModel(app, userId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    fun onClickedSend() {
        val message = "test"
        val roomDocId = "g91yGae3acZ8jg"
        val userDocId = Date().toString()

        viewModelScope.launch {
            messageRepository.saveMessage(
                // TODO: Can be improved
                MessageEntity(
                    id = UUID.randomUUID().toString(),
                    message = message,
                    roomDocId = roomDocId,
                    userDocId = userDocId,
                    createdAt = Date().toString()
                )
            )
        }
        this.messageText.value == ""
        viewModelScope.launch {
            postChat(message, userDocId, roomDocId)
        }
    }

    fun onClickedExit() {
        viewModelScope.launch {
            messageRepository.clearMessage()
        }
    }
}