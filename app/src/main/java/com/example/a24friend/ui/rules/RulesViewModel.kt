package com.example.a24friend.ui.rules

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RulesViewModel: ViewModel() {

    private var _navigateToSurvey = MutableLiveData<Boolean>()
    val navigateToSurvey: LiveData<Boolean>
        get() = _navigateToSurvey

    fun onNavigateToSurvey() {
        _navigateToSurvey.value = true
    }

    fun onSurveyNavigated() {
        _navigateToSurvey.value = false
    }
}