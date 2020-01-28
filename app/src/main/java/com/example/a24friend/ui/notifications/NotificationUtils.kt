package com.example.a24friend.ui.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.example.a24friend.R
import com.example.a24friend.ui.MainActivity

private val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
    val contentIntent = Intent(applicationContext, MainActivity::class.java)

    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val logoImage = BitmapFactory.decodeResource(
        applicationContext.resources,
        R.drawable.logo
    )
    val bigPicStyle = NotificationCompat.BigPictureStyle()
        .bigPicture(logoImage)
        .bigLargeIcon(null)

    val builder = NotificationCompat.Builder(
        applicationContext,
        // notification channel
        applicationContext.getString(R.string.match_notification_channel_id)
    )
        // set title, text and icon to builder
        .setSmallIcon(R.drawable.logo)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)
        // set content intent
        .setContentIntent(contentPendingIntent)
        // add style to builder
        .setStyle(bigPicStyle)
        .setLargeIcon(logoImage)
        // set priority
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())

}

