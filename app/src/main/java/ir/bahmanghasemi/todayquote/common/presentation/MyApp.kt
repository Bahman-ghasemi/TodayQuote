package ir.bahmanghasemi.todayquote.common.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import ir.bahmanghasemi.todayquote.common.data.notification.NotificationService

@HiltAndroidApp
class MyApp : Application(){
    override fun onCreate() {
        super.onCreate()
        NotificationService.setUpNotification(applicationContext)
    }
}