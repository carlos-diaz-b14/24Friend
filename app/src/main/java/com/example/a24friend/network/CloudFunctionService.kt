package com.example.a24friend.network

import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.HttpsCallableResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

suspend fun getUserId(userId: String): String? {
    val userMap = hashMapOf(
        "user_doc_id" to userId
    )
    var functions: FirebaseFunctions = FirebaseFunctions.getInstance()
    return withContext(Dispatchers.IO) {
        functions.getHttpsCallable("get_user")
            .call(userMap)
            .addOnCompleteListener { task: Task<HttpsCallableResult> ->
                if (!task.isSuccessful) {
                    throw CloudFunctionException(task.exception?.message.toString())
                }
            }
            .continueWith { task ->
                var hm = task.result?.data as HashMap<String, String>
                hm["user_doc_id"]
            }.await()
    }
}

suspend fun getCities(): ArrayList<HashMap<String, String>>? {
    var functions: FirebaseFunctions = FirebaseFunctions.getInstance()
    return withContext(Dispatchers.IO) {
        functions.getHttpsCallable("get_city")
            .call()
            .addOnCompleteListener { task: Task<HttpsCallableResult> ->
                if (!task.isSuccessful) {
                    throw CloudFunctionException(task.exception?.message.toString())
                }
            }
            .continueWith { task ->
                var hm = task.result?.data as HashMap<String, ArrayList<HashMap<String, String>>>
                hm["city"]
            }.await()
    }
}

suspend fun getLanguages(): ArrayList<HashMap<String, String>>? {
    var functions: FirebaseFunctions = FirebaseFunctions.getInstance()
    return withContext(Dispatchers.IO) {
        functions.getHttpsCallable("get_language")
            .call()
            .addOnCompleteListener { task: Task<HttpsCallableResult> ->
                if (!task.isSuccessful) {
                    throw CloudFunctionException(task.exception?.message.toString())
                }
            }
            .continueWith { task ->
                var hm = task.result?.data as HashMap<String, ArrayList<HashMap<String, String>>>
                hm["language"]
            }.await()
    }
}

suspend fun postChat(
    message: String,
    userDocId: String,
    roomDocId: String
) {
    val data = hashMapOf(
        "message" to message,
        "user_doc_id" to userDocId,
        "room_doc_id" to roomDocId
    )

    var functions: FirebaseFunctions = FirebaseFunctions.getInstance()
    withContext(Dispatchers.IO) {
        functions.getHttpsCallable("set_user_survey")
            .call(data)
            .addOnCompleteListener { task: Task<HttpsCallableResult> ->
                if (!task.isSuccessful) {
                    throw CloudFunctionException(task.exception?.message.toString())
                }
            }.await()
    }
}

suspend fun setUser(
    userId: String,
    nickname: String,
    city_doc_id: String,
    language: Array<String>,
    token: String
) {
    val data = hashMapOf(
        "user_doc_id" to userId,
        "nickname" to nickname,
        "city_doc_id" to city_doc_id,
        "languages" to language,
        "registration_token" to token
    )

    var functions: FirebaseFunctions = FirebaseFunctions.getInstance()
    withContext(Dispatchers.IO) {
        functions.getHttpsCallable("set_user_survey")
            .call(data)
            .addOnCompleteListener { task: Task<HttpsCallableResult> ->
                if (!task.isSuccessful) {
                    throw CloudFunctionException(task.exception?.message.toString())
                }
            }.await()
    }
}

class CloudFunctionException(msg: String) : Exception(msg)