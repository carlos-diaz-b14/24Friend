package com.example.a24friend.network

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.HttpsCallableResult

//fun getUserId(): String? {
//    val result = callGetUser("user_id")
//        .addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                task.result
//            } else {
//                val e = task.exception
//                Log.e("getUser", e?.message)
//                ""
//            }
//        }
//
//}

fun getUserId(userId: String): Task<HttpsCallableResult> {
    // Create the arguments to the callable function.
    val data = hashMapOf(
        "user_id" to userId
    )
    var region = "us-central1"

    var functions = FirebaseFunctions.getInstance(FirebaseApp.getInstance(), region)

    return functions
        .getHttpsCallable("get_user")
        .call(data)
        .addOnCompleteListener {task: Task<HttpsCallableResult> ->
            if (!task.isSuccessful) {
                throw CloudFunctionException(task.exception?.message.toString())
            }
        }
//        .continueWith { task ->
//            // This continuation runs on either success or failure, but if the task
//            // has failed then result will throw an Exception which will be
//            // propagated down.
//            val result = task.result?.data as String
//            result
//        }
}

class CloudFunctionException(msg: String): Exception(msg)