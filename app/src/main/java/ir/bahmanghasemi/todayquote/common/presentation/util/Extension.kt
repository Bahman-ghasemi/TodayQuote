package ir.bahmanghasemi.todayquote.common.presentation.util

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp

object Extension {

    /**
    this is for rotate text
     **/

    fun Modifier.rotateLayout(degrees: Float): Modifier {
        return when (degrees) {
            0f, 180f -> this
            90f, 270f -> then(HorizontalLayoutModifier)
            else -> this
        } then rotate(degrees)
    }

    private fun Constraints.transpose(): Constraints {
        return copy(
            minWidth = minHeight,
            maxWidth = maxHeight,
            minHeight = minWidth,
            maxHeight = maxWidth
        )
    }

    private object HorizontalLayoutModifier : LayoutModifier {
        override fun MeasureScope.measure(measurable: Measurable, constraints: Constraints): MeasureResult {
            val placeable = measurable.measure(constraints.transpose())
            return layout(placeable.height, placeable.width) {
                placeable.place(
                    x = -(placeable.width / 2 - placeable.height / 2),
                    y = -(placeable.height / 2 - placeable.width / 2)
                )
            }
        }

        override fun IntrinsicMeasureScope.minIntrinsicHeight(measurable: IntrinsicMeasurable, width: Int): Int {
            return measurable.maxIntrinsicWidth(width)
        }

        override fun IntrinsicMeasureScope.maxIntrinsicHeight(measurable: IntrinsicMeasurable, width: Int): Int {
            return measurable.maxIntrinsicWidth(width)
        }

        override fun IntrinsicMeasureScope.minIntrinsicWidth(measurable: IntrinsicMeasurable, height: Int): Int {
            return measurable.minIntrinsicHeight(height)
        }

        override fun IntrinsicMeasureScope.maxIntrinsicWidth(measurable: IntrinsicMeasurable, height: Int): Int {
            return measurable.maxIntrinsicHeight(height)
        }
    }


    @Composable
    fun RowScope.NavigationBarItem(
        selectedIcon: Int,
        selected: Boolean,
        unselectedIcon: Int,
        label: String,
        onclick: () -> Unit
    ) {
        val itemColor = NavigationBarItemColors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            selectedIndicatorColor = Color.Transparent,
            unselectedIconColor = MaterialTheme.colorScheme.onSurface,
            unselectedTextColor = MaterialTheme.colorScheme.onSurface,
            disabledIconColor = MaterialTheme.colorScheme.onTertiaryContainer,
            disabledTextColor = MaterialTheme.colorScheme.onTertiaryContainer
        )

        NavigationBarItem(
            selected = selected,
            onClick = onclick,
            icon = {
                Icon(
                    painter = painterResource(if (selected) selectedIcon else unselectedIcon),
                    contentDescription = "Daily Quote",
                    modifier = Modifier.size(24.dp)
                )
            },
            label = {
                if (selected)
                    Text(text = label)
            },
            colors = itemColor
        )
    }

    fun getTitle(text: String): String {
        val str = text.split(" ")
        return buildString {
            repeat(str.size) {
                val separator = if (it == 0 && str[0].length > 6) "\n" else " "
                append(str[it])
                append(separator)
            }
        }
    }

}