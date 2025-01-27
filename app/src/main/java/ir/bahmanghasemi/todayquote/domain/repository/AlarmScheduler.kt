package ir.bahmanghasemi.todayquote.domain.repository

import ir.bahmanghasemi.todayquote.domain.model.AlarmItem

interface AlarmScheduler {
    fun schedule(alarmItem: AlarmItem)
    fun cancel(alarmItem: AlarmItem)
}