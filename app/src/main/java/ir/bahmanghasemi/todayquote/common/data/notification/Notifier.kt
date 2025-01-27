package ir.bahmanghasemi.todayquote.common.data.notification

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import ir.bahmanghasemi.todayquote.R
import ir.bahmanghasemi.todayquote.common.data.util.Const
import javax.inject.Inject

class Notifier(private val context: Context) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun notify(message: String) {
        val notification = NotificationCompat.Builder(context, Const.NOTIFICATION_CHANNEL_ID)
            .setContentTitle("test title")
            .setContentText(message)
            .setSmallIcon(R.drawable.notification)
//            .addAction(NotificationCompat.ac)
            .build()
        notificationManager.notify(Const.NOTIFICATION_ID, notification)
    }
}