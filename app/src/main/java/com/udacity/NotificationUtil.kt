package com.udacity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

fun NotificationManager.createNotification(messageBody: String, applicationContext: Context,
                                           title:String,status:String) {
    val intent=Intent(applicationContext,DetailActivity::class.java)
    intent.putExtra("fileName", title)
    intent.putExtra("status", status)
    val pendingIntent= PendingIntent.getActivity(
        applicationContext,
        Const.NOTIFICATION_ID,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    val builder=NotificationCompat.Builder(
    applicationContext,
    applicationContext.getString(R.string.channel_id)
    )
        .setContentTitle("Download Result")
        .setContentText(messageBody)
        .setSmallIcon(R.drawable.ic_assistant_black_24dp)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .addAction(R.drawable.ic_assistant_black_24dp,"Get In",pendingIntent)
    notify(Const.NOTIFICATION_ID,builder.build())
}
fun createChannel(context:Context,channelId:String,channelName:String){

   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
       val channel=  NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_HIGH)
       channel.description="notifi"
       channel.enableLights(true)
       channel.enableVibration(true)
       val notificationManager=context.getSystemService(
           NotificationManager::class.java
       )
       notificationManager.createNotificationChannel(channel)
    } else {
        TODO("VERSION.SDK_INT < O")
    }
}