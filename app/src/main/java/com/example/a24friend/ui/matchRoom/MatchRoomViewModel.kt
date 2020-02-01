package com.example.a24friend.ui.matchRoom

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.a24friend.database.asDomainModel
import com.example.a24friend.database.getDatabase
import com.example.a24friend.domain.ChatRoom
import com.example.a24friend.repository.ChatRoomRepository
import com.example.a24friend.repository.MessageRepository
import com.example.a24friend.ui.chatRoom.ChatRoomViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MatchRoomViewModel(application: Application):AndroidViewModel(application) {
    lateinit var userID: String

    var chatRoom: ChatRoom? = null

    private val chatRoomRepository: ChatRoomRepository = ChatRoomRepository(getDatabase(application))

    private val viewModelJob = Job()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    //TODO
    //get userID

    fun getMatch() {
        getMatchFromServer()
        if (chatRoom == null) {
            getMatchFromLocal()
        }
    }

    fun getMatchFromServer() {}

    fun getMatchFromLocal() = viewModelScope.launch {
        chatRoom = chatRoomRepository.getChatRoom(userID)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(val app: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MatchRoomViewModel::class.java)) {
                return MatchRoomViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}