package ir.bahmanghasemi.todayquote.presentation.author.composable

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import ir.bahmanghasemi.todayquote.R
import ir.bahmanghasemi.todayquote.common.presentation.util.UIExtension.shimmerEffect
import ir.bahmanghasemi.todayquote.domain.model.Author
import ir.bahmanghasemi.todayquote.presentation.author.AuthorViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.AuthorsScreen(
    viewModel: AuthorViewModel = hiltViewModel<AuthorViewModel>(),
    animatedVisibilityScope: AnimatedVisibilityScope,
    onItemSelected: (author: Author) -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.uiState.collectAsState()
    val lazyListState = rememberLazyListState()
    val isExpanding by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex < 3
        }
    }
    var searchText by rememberSaveable { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AnimatedVisibility(
            visible = isExpanding,
            enter = expandVertically(),
            exit = shrinkVertically(
                animationSpec = spring(
                    stiffness = Spring.StiffnessLow,
                    dampingRatio = Spring.DampingRatioLowBouncy
                )
            )
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 48.dp, end = 64.dp, top = 24.dp),
                text = "Popular Authors",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black
            )
        }

        AnimatedVisibility(
            visible = !isExpanding,
            enter = slideInVertically(),
            exit = slideOutVertically(
                animationSpec = spring(
                    stiffness = Spring.StiffnessLow,
                    dampingRatio = Spring.DampingRatioLowBouncy
                )
            )
        ) {
            Spacer(modifier = Modifier.height(0.dp))
        }

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(24.dp))
                .clip(RoundedCornerShape(24.dp)),
            value = searchText,
            onValueChange = { searchText = it },
            leadingIcon = {
                Icon(painter = painterResource(R.drawable.search), contentDescription = "Search", tint = Color.Gray)
            },
            placeholder = {
                Text(text = "Write authors name...", color = Color.Gray, fontSize = 14.sp)
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            )
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            state = lazyListState
        ) {
            if (state.isLoading) {
                items(8) {
                    AuthorListItem(null, animatedVisibilityScope, onItemSelected)
                }
            } else {
                state.authors?.let { authors ->
                    itemsIndexed(authors, key = { _, item -> item.id }) { index, author ->
                        AuthorListItem(author, animatedVisibilityScope, onItemSelected)
                    }
                }
            }
        }
    }

    LaunchedEffect(true) {
        viewModel.getAuthors("")
    }

    SideEffect {
        state.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.AuthorListItem(
    author: Author?,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onItemSelected: (author: Author) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable {
                author?.let(onItemSelected)
            }
            .padding(horizontal = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.Start)
    ) {
        Box(contentAlignment = Alignment.Center) {
            AsyncImage(
                modifier = Modifier
                    .size(96.dp)
                    .padding(top = 8.dp, end = 16.dp)
                    .shimmerEffect(author == null)
                    .sharedElement(
                        state = rememberSharedContentState(key = "shape${author?.id}"),
                        animatedVisibilityScope = animatedVisibilityScope
                    ),
                model = author?.shape,
                contentDescription = "Shape",
            )
            Text(
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 16.dp)
                    .shimmerEffect(author == null)
                    .sharedElement(
                        state = rememberSharedContentState(key = "firstWord${author?.id}"),
                        animatedVisibilityScope = animatedVisibilityScope
                    ),
                text = author?.name?.first()?.uppercase() ?: "",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.displayLarge
            )
        }
        Text(
            modifier = Modifier
                .weight(1f)
                .shimmerEffect(author == null)
                .sharedElement(
                    state = rememberSharedContentState(key = "name${author?.id}"),
                    animatedVisibilityScope = animatedVisibilityScope
                ),
            text = author?.name ?: ""
        )
    }
}
