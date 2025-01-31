package ir.bahmanghasemi.todayquote.presentation.favorite.composable

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import ir.bahmanghasemi.todayquote.R
import ir.bahmanghasemi.todayquote.common.presentation.util.UIExtension.shimmerEffect
import ir.bahmanghasemi.todayquote.domain.model.Author
import ir.bahmanghasemi.todayquote.domain.model.Quote
import ir.bahmanghasemi.todayquote.presentation.favorite.FavoriteViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel<FavoriteViewModel>(),
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    val context = LocalContext.current
    val state by viewModel.favoriteUiState.collectAsState()
    val lazyListState = rememberLazyListState()

    Column(
        Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 48.dp, end = 64.dp, top = 24.dp),
            text = "Your Favorites",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item { }
            if (state.isLoading) {
                items(8) {
                    FavoriteListItem(favorite = null, animatedVisibilityScope = animatedVisibilityScope) {

                    }
                }
            } else {
                state.favorites?.let { favorites ->
                    itemsIndexed(favorites, key = { _, item -> item.id }) { index, favorite ->
                        FavoriteListItem(favorite = favorite, animatedVisibilityScope = animatedVisibilityScope, index = index) {}
                    }
                    item { }
                }
            }
        }
    }

    LaunchedEffect(true) {
        viewModel.getFavorites()
    }

    SideEffect {
        state.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.FavoriteListItem(
    favorite: Quote?,
    index: Int? = null,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onItemSelected: (author: Author) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.Top) {
            Column(
                Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shimmerEffect(favorite == null)
                        .sharedElement(
                            state = rememberSharedContentState(key = "content${favorite?.id}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        ),
                    text = favorite?.content ?: "",
                    lineHeight = TextUnit(20f, TextUnitType.Sp),
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shimmerEffect(favorite == null)
                        .sharedElement(
                            state = rememberSharedContentState(key = "author${favorite?.id}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        ),
                    text = buildAnnotatedString {
                        favorite?.let {
                            append("———")
                            append(" ")
                            append(favorite.author)
                        } ?: ""
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.LightGray
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.Top),
                horizontalAlignment = Alignment.End
            ) {
                val color = index?.let {
                    val idx = if (index > 20) index % 20 else index
                    ir.bahmanghasemi.todayquote.common.data.util.Color.getColors()[idx]
                } ?: ir.bahmanghasemi.todayquote.common.data.util.Color.random()



                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .shimmerEffect(favorite == null)
                        .sharedElement(
                            state = rememberSharedContentState(key = "shape${favorite?.id}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = R.drawable.favorite_shape,
                        contentDescription = "Shape",
                        colorFilter = ColorFilter.tint(color)
                    )

                    Text(
                        text = favorite?.author?.firstOrNull().toString(),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.background
                    )
                }

                Row {
                    IconButton(onClick = {
                        favorite?.let { favorite ->
                            when (favorite.isFavorite) {
                                true -> {
//                                viewModel.makeUnFavorite(it)
                                }

                                false -> {
//                                viewModel.makeFavorite(it)
                                }
                            }
                        }
                    }) {
                        Icon(
                            painter = painterResource(if (favorite?.isFavorite == true) R.drawable.heart_fill else R.drawable.heart),
                            contentDescription = "Favorite"
                        )
                    }
                    IconButton(onClick = {
                        // Todo: Implement Share Functionality
                    }) {
                        Icon(painter = painterResource(R.drawable.share), contentDescription = "Share")
                    }
                }
            }
        }
    }
}