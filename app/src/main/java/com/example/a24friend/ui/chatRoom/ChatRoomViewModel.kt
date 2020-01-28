package com.example.a24friend.ui.chatRoom

import android.app.Application
import androidx.lifecycle.*
import com.example.a24friend.database.getDatabase
import com.example.a24friend.domain.Message
import com.example.a24friend.repository.MessageRepository
import kotlinx.coroutines.*
import timber.log.Timber
import java.io.IOException

enum class ApiStatus { LOADING, DONE, ERROR }

class ChatRoomViewModel(application: Application) : AndroidViewModel(application) {

    private val messageRepository: MessageRepository = MessageRepository(getDatabase(application))

    val message: LiveData<List<Message>> = messageRepository.message

    private val viewModelJob = Job()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var _networkErrorStatus = MutableLiveData<ApiStatus>(ApiStatus.LOADING)
    val networkErrorStatus: LiveData<ApiStatus>
        get() = _networkErrorStatus

    fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                messageRepository.refreshMessage()
                _networkErrorStatus.value = ApiStatus.DONE

            } catch (networkError: IOException) {
                if (message.value!!.isEmpty()) {
                    // Toast
                    _networkErrorStatus.value = ApiStatus.ERROR
                }
            }
        }
    }

    fun clearDataFromRepository() = viewModelScope.launch {
        messageRepository.clearContacts()
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