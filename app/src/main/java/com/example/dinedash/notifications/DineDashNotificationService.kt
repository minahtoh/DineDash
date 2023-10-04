package com.example.dinedash.notifications

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.dinedash.R
import com.example.dinedash.activities.main.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

private const val CHANNEL_ID = "Firebase Notification"
class DineDashNotificationService: FirebaseMessagingService() {
    @SuppressLint("ObsoleteSdkInt")
    override fun onMessageReceived(message: RemoteMessage) {

        val intent = Intent(this, MainActivity::class.java)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationID = Random.nextInt()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            createNotificationChannel(notificationManager)
        }


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(message.notification?.title)
            .setContentText(message.notification?.body)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(notificationID,notification)
    }

    override fun onNewToken(token: String) {
        Log.d("Firebase Notification", "Refreshed token: $token")
    }


    private fun createNotificationChannel(notificationManager: NotificationManager){
        val channelName = "Firebase Notifications"
        val channel = NotificationChannel(CHANNEL_ID,channelName,NotificationManager.IMPORTANCE_HIGH).apply {
            description = ""
            enableLights(true)
        }
        notificationManager.createNotificationChannel(channel)
    }
}