package com.example.a24friend.ui.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.a24friend.R
import com.example.a24friend.database.UserDao
import com.example.a24friend.database.UserEntity
import com.example.a24friend.database.getDatabase
import com.example.a24friend.ui.MainActivity
import kotlinx.coroutines.*

class StartFragment : Fragment() {

    private lateinit var database: UserDao
    private lateinit var activity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as MainActivity

        database = getDatabase(requireContext()).userDao
        initialize()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    private fun initialize() {
        CoroutineScope(Dispatchers.Main + Job()).launch {
            // TODO delete this later
            clearUser()

            var userInfo = getUserInfo()
            val navController = findNavController()
            when {
                userInfo == null -> navController.navigate(R.id.action_startFragment_to_rulesFragment)
                userInfo.nickname == null -> {
                    activity.mUserId = userInfo.userId
                    navController.navigate(R.id.action_startFragment_to_surveyFragment)
                }
                else -> {
                    activity.mUserId = userInfo.userId
                    navController.navigate(R.id.action_startFragment_to_matchRoomFragment)
                }
            }
        }
    }

    private suspend fun getUserInfo(): UserEntity? {
        return withContext(Dispatchers.IO) {
            var user = database.getUser()
            user
        }
    }

    private suspend fun clearUser(){
        return withContext(Dispatchers.IO) {
            database.clear()
        }
    }

}
