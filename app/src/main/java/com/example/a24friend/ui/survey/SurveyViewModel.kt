package com.example.a24friend.ui.survey

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.a24friend.database.UserDao
import com.example.a24friend.database.UserEntity
import com.example.a24friend.network.getCities
import com.example.a24friend.network.getUserId
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

private const val TAG = "SurveyViewModel"

class SurveyViewModel(
    val database: UserDao,
    val uid: String,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val dbScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    private var displayUser = MutableLiveData<UserEntity?>()
    private val _userId = MutableLiveData<String?>()
    val userId: LiveData<String?>
        get() = _userId
    private val _user = MutableLiveData<UserEntity>()
    val user: LiveData<UserEntity>
        get() = _user
    private val _cities = MutableLiveData<Array<String>>()
    val cities: LiveData<Array<String>>
        get() = _cities
    private val _langueges = MutableLiveData<Array<String>>()
    val languages: LiveData<Array<String>>
        get() = _langueges
    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean>
        get() = _error

    init {
        _error.value = false
        when (uid) {
            "" -> initializeUserInfo()
            else -> _userId.value = uid
        }
    }

    private fun setCityOptions() {
        viewModelScope.launch {
            try {
                var citiesHm = getCities()
                if (citiesHm.isNullOrEmpty()) {
                    return@launch
                }
                var keys = citiesHm?.keys
                var cityArray = mutableListOf<String>()
                for (key in keys) {
                    cityArray.add(citiesHm[key]!!)
                }
                _cities.value = cityArray.toTypedArray()
            } catch (e: Exception) {
                Log.e(TAG, e.message)
                _error.value = true
            }
        }
    }

    private fun setLanguageOptions() {

    }

    private fun initializeUserInfo() {
        // create userInfo when the survey is opened for the first time
        // cloud function
        viewModelScope.launch {
            try {
                _userId.value = getUserId(uid)
            } catch (e: Exception) {
                Log.e(TAG, e.message)
                _error.value = true
            }
        }
    }

    fun setUser() {
        setUserInfo()
    }

    private fun setUserInfo() {
        // get user to display default value
        dbScope.launch {
            var result = database.getUser()
            // register user if it hasn't registered
            if (result == null) {
                val token = getToken()
                result = UserEntity(_userId.value!!, token, null, null, null)
                database.insert(result)
            }
            _user.value = result
        }
    }

    private suspend fun getToken(): String {
        return withContext(Dispatchers.IO) {
            FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(TAG, "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }
                })
                .continueWith { task ->
                    // Get new Instance ID token
                    val token = task.result?.token
                    // Log and toast
                    Log.d(TAG, "Token: $token")

                    token!!
                }.await()
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
        dbScope.cancel()
    }

// TODO insert userInfo when "done" is tapped and userInfo hasn't created
//    fun insertUserInfo() {
//        if (_user.value != null) {
//            database.insert(_user.value!!)
//        }
//    }

// TODO update userInfo when "done" is tapped and userInfo has already created

// TODO required check

// TODO convert language list into string

}