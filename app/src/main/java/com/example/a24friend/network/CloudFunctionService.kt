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
        "user_doc_id" to userId
    )

    var functions = FirebaseFunctions.getInstance()

    return functions
        .getHttpsCallable("get_user")
        .call(data)
        .addOnCompleteListener {task: Task<HttpsCallableResult> ->
            if (!task.isSuccessful) {
                throw CloudFunctionException(task.exception?.message.toString())
            }
        }
}

class CloudFunctionException(msg: String): Exception(msg)