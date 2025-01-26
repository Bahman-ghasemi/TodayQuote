package ir.bahmanghasemi.todayquote.common.presentation.util

import android.graphics.BlurMaskFilter
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.addOutline
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
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
        selected: Boolean,
        @DrawableRes selectedIcon: Int,
        @DrawableRes unselectedIcon: Int,
        label: String,
        onclick: () -> Unit
    ) {

        NavigationBarItem(
            selected = selected,
            onClick = onclick,
            icon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(if (selected) selectedIcon else unselectedIcon),
                    contentDescription = "Daily Quote",
                    tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                )
            },
            label = {
                AnimatedVisibility(selected) {
                    Text(text = label, color = MaterialTheme.colorScheme.primary)
                }
            },
            colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
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

    fun Modifier.shimmerEffect(
        isLoading: Boolean = false, // <-- New parameter for start/stop.
        widthOfShadowBrush: Int = 500,
        angleOfAxisY: Float = 270f,
        durationMillis: Int = 1000,
    ): Modifier {
        if (!isLoading) { // <-- Step 1.
            return this
        } else {
            return composed {

                // Step 2.
                val shimmerColors = ShimmerAnimationData(isLightMode = false /*!isSystemInDarkTheme()*/).getColours()

                val transition = rememberInfiniteTransition(label = "")

                val translateAnimation = transition.animateFloat(
                    initialValue = 0f,
                    targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
                    animationSpec = infiniteRepeatable(
                        animation = tween(
                            durationMillis = durationMillis,
                            easing = LinearEasing,
                        ),
                        repeatMode = RepeatMode.Restart,
                    ),
                    label = "Shimmer loading animation",
                )

                this.background(
                    brush = Brush.linearGradient(
                        colors = shimmerColors,
                        start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
                        end = Offset(x = translateAnimation.value, y = angleOfAxisY),
                    ),
                )
            }
        }
    }

    data class ShimmerAnimationData(
        private val isLightMode: Boolean
    ) {
        fun getColours(): List<Color> {
            return if (isLightMode) {
                val color = Color.White

                listOf(
                    color.copy(alpha = 0.3f),
                    color.copy(alpha = 0.5f),
                    color.copy(alpha = 1.0f),
                    color.copy(alpha = 0.5f),
                    color.copy(alpha = 0.3f),
                )
            } else {
                val color = Color.Black

                listOf(
                    color.copy(alpha = 0.0f),
                    color.copy(alpha = 0.3f),
                    color.copy(alpha = 0.5f),
                    color.copy(alpha = 0.3f),
                    color.copy(alpha = 0.0f),
                )
            }
        }
    }

}