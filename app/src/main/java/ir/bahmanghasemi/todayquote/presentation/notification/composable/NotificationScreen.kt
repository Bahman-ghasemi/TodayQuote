package ir.bahmanghasemi.todayquote.presentation.notification.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ir.bahmanghasemi.todayquote.R
import ir.bahmanghasemi.todayquote.common.data.data_source.pref.encryptedPreferences
import ir.bahmanghasemi.todayquote.common.data.util.Const
import ir.bahmanghasemi.todayquote.common.presentation.component.TimeType
import ir.bahmanghasemi.todayquote.common.presentation.component.VerticalTime
import ir.bahmanghasemi.todayquote.data.repository.AlarmSchedulerImpl
import ir.bahmanghasemi.todayquote.domain.model.AlarmItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun NotificationScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val alarmScheduler = AlarmSchedulerImpl(context)

    var isChecked by remember { mutableStateOf(context.encryptedPreferences().get(Const.DAILY_NOTIFICATION_ENABLED, false)) }
    var selectedHour by remember { mutableIntStateOf(context.encryptedPreferences().get(Const.DAILY_NOTIFICATION_HOUR, 0)) }
    var selectedMinute by remember { mutableIntStateOf(context.encryptedPreferences().get(Const.DAILY_NOTIFICATION_MINUTE, 0)) }

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp, end = 40.dp),
                text = "Notification",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Daily",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Switch(
                    modifier = Modifier
                        .padding(horizontal = 2.dp),
                    checked = isChecked,
                    onCheckedChange = {
                        isChecked = it
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.background,
                        checkedIconColor = MaterialTheme.colorScheme.primary,
                        checkedTrackColor = MaterialTheme.colorScheme.primary

                    ),
                    thumbContent = {
                        when (isChecked) {
                            true -> Text(text = "On", fontWeight = FontWeight.Bold)
                            false -> Text(text = "Off", fontWeight = FontWeight.Bold)
                        }
                    }
                )
            }

            Text(
                text = "Set the daily notification time.",
                style = MaterialTheme.typography.bodyMedium
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .pointerInput(isChecked) {
                        if (!isChecked) {
                            awaitPointerEventScope { while (true) awaitPointerEvent() }
                        }
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterHorizontally)
            ) {
                VerticalTime(type = TimeType.HOUR, initialTime = selectedHour, enabled = isChecked) {
                    selectedHour = it
                }

                Text(
                    text = ":",
                    style = MaterialTheme.typography.displayLarge,
                    color = if (isChecked) LocalContentColor.current else IconButtonDefaults.iconButtonColors().disabledContentColor
                )

                VerticalTime(type = TimeType.MINUTE, initialTime = selectedMinute, enabled = isChecked) {
                    selectedMinute = it
                }
            }

        }

        AsyncImage(
            modifier = Modifier.fillMaxHeight(),
            model = R.drawable.abstract_shape_notification,
            contentDescription = "notification",
            contentScale = ContentScale.FillWidth,
            alignment = Alignment.BottomCenter
        )
    }

    LaunchedEffect(isChecked, selectedHour, selectedMinute) {
        scope.launch {
            delay(3000)
            val date = LocalDate.now()
            val time = LocalTime.of(selectedHour, selectedMinute)
            val dateTime = LocalDateTime.of(date, time)

            val alarmItem = AlarmItem(
                dateTime = dateTime,
                message = "Click for showing today quote..."
            )

            println("selectedTime: $time")
            println("alarmItem: $alarmItem")

            if (isChecked) {
                context.encryptedPreferences().put(Const.DAILY_NOTIFICATION_ENABLED, isChecked)
                context.encryptedPreferences().put(Const.DAILY_NOTIFICATION_HOUR, selectedHour)
                context.encryptedPreferences().put(Const.DAILY_NOTIFICATION_MINUTE, selectedMinute)
                alarmScheduler.schedule(alarmItem)
            } else {
                context.encryptedPreferences().clear()
                alarmScheduler.cancel(alarmItem)
            }
        }
    }
}