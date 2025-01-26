package ir.bahmanghasemi.todayquote.common.presentation.util

object Extension {

    fun Number.in24Hour(): String {
        return String.format(format = "%02d", this)
    }
}