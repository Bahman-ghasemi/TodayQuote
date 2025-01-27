package ir.bahmanghasemi.todayquote.domain.model

import java.time.LocalDateTime

data class AlarmItem(
    val dateTime: LocalDateTime,
    val message: String
)
