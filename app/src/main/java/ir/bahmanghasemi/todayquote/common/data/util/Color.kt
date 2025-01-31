package ir.bahmanghasemi.todayquote.common.data.util

import androidx.compose.ui.graphics.Color

object Color {
    fun getColors() = listOf<Color>(
        Color(0xfff7903e),
        Color(0xffa3b18a),
        Color(0xffFF758F),
        Color(0xff136cb2),
        Color(0xff007F5F),
        Color(0xff6c757d),
        Color(0xfffff3b0),
        Color(0xffFFC6FF),
        Color(0xff748cab),
        Color(0xff7B2CBF),
        Color(0xffFFFF3F),
        Color(0xffFFDDD2),
        Color(0xff38A3A5),
        Color(0xff2a9447),
        Color(0xffE29578),
        Color(0xffe0a7ae),
        Color(0xff34A0A4),
        Color(0xfff9a03f),
        Color(0xff84a98c),
    )

    fun random() = getColors().random()
}