package ir.bahmanghasemi.todayquote.presentation.daily_quote.composable

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.bahmanghasemi.todayquote.R
import ir.bahmanghasemi.todayquote.common.presentation.util.UIExtension.getTitle
import ir.bahmanghasemi.todayquote.common.presentation.util.UIExtension.rotateLayout
import ir.bahmanghasemi.todayquote.common.presentation.util.UIExtension.shimmerEffect
import ir.bahmanghasemi.todayquote.presentation.daily_quote.QuoteViewModel

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun DailyQuoteScreen(
    viewModel: QuoteViewModel = hiltViewModel<QuoteViewModel>(),
) {
    val context = LocalContext.current
    val state by viewModel.quoteUiState.collectAsStateWithLifecycle()
    val showOperations by remember {
        derivedStateOf {
            state.quote != null
        }
    }

    Row(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .weight(0.15f)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.tertiary)
                .padding(top = 48.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                modifier = Modifier
                    .rotateLayout(270f)
                    .shimmerEffect(isLoading = state.isLoading),
                text = state.quote?.author ?: "",
                fontSize = 16.sp,
                letterSpacing = TextUnit(2f, TextUnitType.Sp),
                color = MaterialTheme.colorScheme.onTertiary
            )
        }

        VerticalDivider(thickness = 8.dp, color = MaterialTheme.colorScheme.primary)

        Column(modifier = Modifier.weight(1f)) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AnimatedVisibility(
                    visible = showOperations,
                    enter = expandHorizontally() + slideInHorizontally(),
                    exit = shrinkHorizontally() + slideOutHorizontally()
                ) {
                    IconButton(modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(topStart = 0.dp, bottomStart = 0.dp, topEnd = 8.dp, bottomEnd = 8.dp))
                        .background(MaterialTheme.colorScheme.primary),
                        onClick = {
                            viewModel.getRandomQuote()
                        }) {

                        Icon(
                            modifier = Modifier.padding(4.dp),
                            painter = painterResource(R.drawable.next),
                            contentDescription = "Next",
                            tint = MaterialTheme.colorScheme.background
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                AnimatedVisibility(
                    visible = showOperations,
                    enter = expandHorizontally() + slideInHorizontally { fullWidth -> fullWidth },
                    exit = shrinkHorizontally() + slideOutHorizontally { fullWidth -> fullWidth }
                ) {
                    Row {
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
                }
            }

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(start = 12.dp, end = 12.dp, bottom = 48.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(space = 12.dp, alignment = Alignment.Bottom)
            ) {
                val words = state.quote?.content?.split(" ")
                val title = words?.let {
                    getTitle(words.take(3).joinToString(" "))
                }
                val description = words?.drop(3)?.joinToString(" ")
                Text(
                    modifier = Modifier.shimmerEffect(isLoading = state.isLoading),
                    text = title?.uppercase() ?: "",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    modifier = Modifier.shimmerEffect(isLoading = state.isLoading),
                    text = description ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }

    LaunchedEffect(true) {
        viewModel.getRandomQuote()
    }
    SideEffect {
        state.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
}