package com.example.a24friend.ui.survey

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.a24friend.database.UserDao
import com.example.a24friend.database.UserEntity
import com.example.a24friend.network.getCities
import com.example.a24friend.network.getLanguages
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
    private val _userId = MutableLiveData<String?>()
    val userId: LiveData<String?>
        get() = _userId
    private val _user = MutableLiveData<UserEntity>()
    val user: LiveData<UserEntity>
        get() = _user
    private val _cities = MutableLiveData<HashMap<String, String>>()
    val cities: LiveData<HashMap<String, String>>
        get() = _cities
    private val _languages = MutableLiveData<HashMap<String, String>>()
    val languages: LiveData<HashMap<String, String>>
        get() = _languages
    private val _selectedLanguage = MutableLiveData<String>()
    val selectedLanguage: LiveData<String>
        get() = _selectedLanguage
    private val _selectedCity = MutableLiveData<String>()
    val selectedCity: LiveData<String>
        get() = _selectedCity

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean>
        get() = _error
    private val _navigateToMatch = MutableLiveData<Boolean>()
    val navigateToMatch: LiveData<Boolean>
        get() = _error


    init {
        _error.value = false
        setCityOptions()
        setLanguageOptions()
//        when (uid) {
//            "" -> initializeUserInfo()
//            else -> _userId.value = uid
//        }
    }

    private fun setCityOptions() {
        viewModelScope.launch {
            try {
                var cityL = getCities()
                if (cityL.isNullOrEmpty()) {
                    return@launch
                }

                val spinnerMap = HashMap<String, String>()
                for (cityMap in cityL) {
                    spinnerMap.put(cityMap["doc_id"]!!, cityMap["name"]!!)
                }
                _cities.value = spinnerMap

            } catch (e: Exception) {
                Log.e(TAG, e.message)
                _error.value = true
            }
        }
    }

    private fun setLanguageOptions() {
        viewModelScope.launch {
            try {
                var languageL = getLanguages()
                if (languageL.isNullOrEmpty()) {
                    return@launch
                }

                val spinnerMap = HashMap<String, String>()
                for (languageMap in languageL) {
                    spinnerMap.put(languageMap["doc_id"]!!, languageMap["name"]!!)
                }
                _languages.value = spinnerMap

            } catch (e: Exception) {
                Log.e(TAG, e.message)
                _error.value = true
            }
        }
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
        _user.value = setUserInfo()
        if (_user.value!!.city.isNullOrBlank()) {
            _selectedCity.value = _cities.value?.get(_user.value!!.city)
        }
        if (_user.value!!.language.isNullOrBlank()) {
            _selectedLanguage.value = _languages.value?.get(_user.value!!.language)
        }
    }

    private fun setUserInfo(): UserEntity? {
        // get user to display default value
        runBlocking(Dispatchers.Default) {
            withContext(Dispatchers.IO) {
                var result = database.getUser()
                if (result == null) {
                    val token = getToken()
                    result = UserEntity(_userId.value!!, token, null, null, null)
                    database.insert(result)
                }
                return@withContext result
            }
        }
        return null
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

    // TODO update userInfo when "done" is tapped and userInfo hasn't created
    fun updateUserInfo() {
        database.insert(_user.value!!)
        _navigateToMatch.value = true
    }

    fun selectCity(newCity: String) {
        _selectedCity.value = newCity
    }

    fun selectLanguage(newLanguage: String) {
        _selectedLanguage.value = newLanguage
    }

// TODO required check

// TODO convert language list into string

}