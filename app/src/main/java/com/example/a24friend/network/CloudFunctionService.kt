package com.example.a24friend.network

import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions

class CloudFunctionService {

    companion object {
        private var functions = FirebaseFunctions.getInstance()
    }

    private fun getUserId(userId: String): Task<String> {
        // Create the arguments to the callable function.
        val data = hashMapOf(
            "user_id" to userId
        )

        return functions
            .getHttpsCallable("get_user")
            .call(data)
            .continueWith { task ->
                // This continuation runs on either success or failure, but if the task
                // has failed then result will throw an Exception which will be
                // propagated down.
                val result = task.result?.data as String
                result
            }
    }

//    getUserId("user_id")
//    .addOnCompleteListener(OnCompleteListener { task ->
//        if (!task.isSuccessful) {
//            val e = task.exception
//            if (e is FirebaseFunctionsException) {
//                val code = e.code
//                val details = e.details
//            }
//        }
//    })

}