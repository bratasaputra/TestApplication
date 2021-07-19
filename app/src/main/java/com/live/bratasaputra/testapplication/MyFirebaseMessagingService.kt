package com.live.bratasaputra.testapplication

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {
  override fun onNewToken(token: String) {
    Log.d("Firebase", "FCM Token: $token")
  }

  override fun onMessageReceived(message: RemoteMessage) {
    Log.d("Firebase", "FCM Received ${message.from}")
  }
}