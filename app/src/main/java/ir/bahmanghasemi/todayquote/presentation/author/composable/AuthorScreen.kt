package ir.bahmanghasemi.todayquote.presentation.author.composable

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.room.util.newStringBuilder
import coil3.compose.AsyncImage
import ir.bahmanghasemi.todayquote.R
import ir.bahmanghasemi.todayquote.common.presentation.ui.theme.LocalFontFamilySupraRounded
import ir.bahmanghasemi.todayquote.common.presentation.ui.theme.fontFamilySupraRounded
import ir.bahmanghasemi.todayquote.domain.model.Author
import ir.bahmanghasemi.todayquote.presentation.daily_quote.QuoteViewModel

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalFoundationApi::class)
@Composable
fun SharedTransitionScope.AuthorScreen(
    viewmodel: QuoteViewModel = hiltViewModel<QuoteViewModel>(),
    animatedVisibilityScope: AnimatedVisibilityScope,
    author: Author,
    onBackPressed: () -> Unit
) {
    val state by viewmodel.quotesUiState.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        IconButton(onClick = { onBackPressed() }) {
            Icon(modifier = Modifier.rotate(180f), painter = painterResource(R.drawable.next), contentDescription = "Back")
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier.sharedElement(
                    state = rememberSharedContentState(key = "name${author.id}"),
                    animatedVisibilityScope = animatedVisibilityScope
                ),
                text = author.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = author.description,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(150.dp)
                        .padding(top = 8.dp, end = 16.dp)
                        .sharedElement(
                            state = rememberSharedContentState(key = "shape${author.id}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        ),
                    model = author.shape,
                    contentDescription = "Shape",
                )
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp, bottom = 16.dp)
                        .sharedElement(
                            state = rememberSharedContentState(key = "firstWord${author.id}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        ),
                    text = author.name.first().uppercase(),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.displayLarge
                )
            }
        }

        HorizontalDivider(
            Modifier
                .fillMaxWidth()
                .shadow(2.dp),
            color = Color.Transparent
        )

        Text(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            text = "Quotes",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontFamily = LocalFontFamilySupraRounded.current
        )

        state.quotes?.let { quotes ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                state = rememberLazyListState()
            ) {
                items(quotes) { quote ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.primary,
                                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                                    )
                                ) {
                                    append("•")
                                }
                                append("  ")
                                append(quote.content)
                            },
                            softWrap = true
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(true) {
        viewmodel.getAuthorQuotes(author.slug)
    }
}