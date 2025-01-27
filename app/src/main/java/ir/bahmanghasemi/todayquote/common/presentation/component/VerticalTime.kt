package ir.bahmanghasemi.todayquote.common.presentation.component

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.bahmanghasemi.todayquote.R
import ir.bahmanghasemi.todayquote.common.presentation.util.Extension.in24Hour
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

@Composable
@Preview(showBackground = true)
fun VerticalTime(type: TimeType = TimeType.HOUR, enabled: Boolean = true, onTimeChanged: (Int) -> Unit = {}) {
    val scope = rememberCoroutineScope()
    var isPressed by remember { mutableStateOf(false) }

    var selectedTime by remember { mutableIntStateOf(if (type == TimeType.HOUR) ZonedDateTime.now().hour else ZonedDateTime.now().minute) }
    val maxValue = if (type == TimeType.HOUR) 23 else 59

    val isUpEnabled by remember(selectedTime, maxValue) {
        derivedStateOf {
            selectedTime < maxValue
        }
    }
    val isDownEnabled by remember(selectedTime) {
        derivedStateOf { selectedTime > 0 }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            onClick = {
                selectedTime += 1
                onTimeChanged(selectedTime)
            },
            enabled = (enabled && isUpEnabled),
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        scope.launch {
                            while (isPressed && selectedTime < maxValue) {
                                selectedTime += 1
                                delay(100) // Adjust delay for faster/slower increments
                            }
                        }
                        tryAwaitRelease() // Wait until the press is released
                        isPressed = false
                    }
                )
            },
        ) {
            Icon(
                modifier = Modifier.padding(8.dp),
                painter = painterResource(R.drawable.up),
                contentDescription = "Up",
                tint = if (enabled && isUpEnabled) MaterialTheme.colorScheme.primary else LocalContentColor.current
            )
        }

        Text(
            text = selectedTime.in24Hour(),
            style = MaterialTheme.typography.displayLarge,
            color = if (enabled) LocalContentColor.current else IconButtonDefaults.iconButtonColors().disabledContentColor
        )

        IconButton(
            onClick = {
                selectedTime -= 1
                onTimeChanged(selectedTime)
            },
            enabled = (enabled && isDownEnabled),
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        scope.launch {
                            while (isPressed && selectedTime > 0) {
                                selectedTime -= 1
                                delay(100) // Adjust delay for faster/slower increments
                            }
                        }
                        tryAwaitRelease() // Wait until the press is released
                        isPressed = false
                    }
                )
            },
        ) {
            Icon(
                modifier = Modifier
                    .padding(8.dp)
                    .rotate(180f),
                painter = painterResource(R.drawable.up),
                contentDescription = "Down",
                tint = if (enabled && isDownEnabled) MaterialTheme.colorScheme.primary else LocalContentColor.current
            )
        }
    }
}