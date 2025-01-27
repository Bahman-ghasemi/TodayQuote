package ir.bahmanghasemi.todayquote.common.presentation.util

object Extension {

    fun Number.in24Hour(): String {
        return String.format(format = "%02d", this)
    }

    fun Int.min(other: Int): Int {
        return if (this < other) this else other
    }

    fun Int.max(other: Int): Int {
        return if (this > other) this else other
    }
}