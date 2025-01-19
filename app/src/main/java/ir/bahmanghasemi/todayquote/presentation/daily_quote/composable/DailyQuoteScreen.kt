package ir.bahmanghasemi.todayquote.presentation.daily_quote.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.bahmanghasemi.todayquote.R
import ir.bahmanghasemi.todayquote.common.presentation.util.Extension.getTitle
import ir.bahmanghasemi.todayquote.common.presentation.util.Extension.rotateLayout
import ir.bahmanghasemi.todayquote.domain.model.Quote

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun DailyQuoteScreen(
    // Todo: fix the values
    quote: Quote? = null,
    tintColor: Color = Color.Red
) {
    Row(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .weight(0.15f)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.tertiary)
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top
        ) {
            Text(
                modifier = Modifier.rotateLayout(270f),
                text = "Farhan Akhtar"
            )
        }

        VerticalDivider(thickness = 4.dp, color = tintColor)

        Column(modifier = Modifier.weight(1f)) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(topStart = 0.dp, bottomStart = 0.dp, topEnd = 8.dp, bottomEnd = 8.dp))
                    .background(tintColor), onClick = {
                    // Todo: implement get next quo
                }) {
                    Icon(
                        painter = painterResource(R.drawable.next),
                        contentDescription = "Next",
                        tint = MaterialTheme.colorScheme.background
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    // Todo: Implement Favorite Functionality
                }) {
                    Icon(painter = painterResource(R.drawable.heart), contentDescription = "Favorite")
                }

                IconButton(onClick = {
                    // Todo: Implement Share Functionality
                }) {
                    Icon(painter = painterResource(R.drawable.share), contentDescription = "Share")
                }
            }

            Column(
                Modifier.fillMaxSize().padding(start = 12.dp, end = 12.dp, bottom = 48.dp ),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(space = 12.dp, alignment = Alignment.Bottom)
            ) {
                val words = "Friendship brings in a lot of honesty and trust into any relationship, especially a marriage".split(" ")
                val title = getTitle(words.take(3).joinToString(" "))
                val description = words.drop(3).joinToString(" ")
                Text(text = title.uppercase(), style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(text = description, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.outline)
            }
        }
    }
}