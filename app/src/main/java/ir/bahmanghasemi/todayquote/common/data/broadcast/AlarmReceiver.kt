package ir.bahmanghasemi.todayquote.common.data.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import ir.bahmanghasemi.todayquote.common.data.notification.Notifier

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        println("receiver launched!")
        val message = intent?.getStringExtra(Intent.EXTRA_TEXT) ?: return
        println("Alarm Received: $message")
        context?.let {
            Notifier(it).notify(message)
        }
    }
}