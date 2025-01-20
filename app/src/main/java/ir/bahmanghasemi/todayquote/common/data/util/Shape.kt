package ir.bahmanghasemi.todayquote.common.data.util

import ir.bahmanghasemi.todayquote.R

class Shape {
    companion object {

        fun shapes(): List<Int> {
            return listOf(
                R.drawable.abstract_shape_1,
                R.drawable.abstract_shape_2,
                R.drawable.abstract_shape_3,
                R.drawable.abstract_shape_4,
                R.drawable.abstract_shape_5,
                R.drawable.abstract_shape_6,
                R.drawable.abstract_shape_7,
                R.drawable.abstract_shape_8,
                R.drawable.abstract_shape_9,
                R.drawable.abstract_shape_10,
                R.drawable.abstract_shape_11,
                R.drawable.abstract_shape_12,
                R.drawable.abstract_shape_13,
                R.drawable.abstract_shape_14,
                R.drawable.abstract_shape_15,
                R.drawable.abstract_shape_16,
                R.drawable.abstract_shape_17,
                R.drawable.abstract_shape_18,
                R.drawable.abstract_shape_19,
                R.drawable.abstract_shape_20,
            )
        }

        fun random(): Int = shapes().random()
    }
}