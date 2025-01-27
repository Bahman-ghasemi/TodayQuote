package ir.bahmanghasemi.todayquote.common.data.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import ir.bahmanghasemi.todayquote.common.data.util.Const

object NotificationService {

    fun setUpNotification(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Const.NOTIFICATION_CHANNEL_ID,
                Const.NOTIFICATION_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
    }
}