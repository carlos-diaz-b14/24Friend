package com.example.a24friend.ui.survey

import android.app.Application
import android.app.Dialog
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.a24friend.database.UserDao
import com.example.a24friend.database.UserEntity
import com.example.a24friend.network.getUserId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class SurveyViewModel(
    val database: UserDao,
    val userId: String,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var displayUser = MutableLiveData<UserEntity?>()
    private val _user = MutableLiveData<UserEntity>()
    val user: LiveData<UserEntity>
        get() = _user
    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean>
        get() = _error

    init {
        _error.value = false
        when (userId) {
            "" -> createUserInfo()
            else -> getUserInfo()
        }
    }

    // TODO create userInfo when the survey is opened for the first time
    fun createUserInfo() {
        _error.value = false
        try {
            var result = getUserId(userId)
            result
                .continueWith { task ->
                    // This continuation runs on either success or failure, but if the task
                    // has failed then result will throw an Exception which will be
                    // propagated down.
                    val result = task.result?.data as String
                    _user.value = UserEntity(result, "", "", "", "")
                }
        } catch (e: Exception) {
            Log.e("callGetUser", e.message)
            _error.value = true
        }
    }

    // TODO get userInfo to display default value
    fun getUserInfo() {
    }

    // TODO insert userInfo when "done" is tapped and userInfo hasn't created

    // TODO update userInfo when "done" is tapped and userInfo has already created

    // TODO required check

    // TODO convert language list into string

}