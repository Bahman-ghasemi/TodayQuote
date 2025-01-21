package ir.bahmanghasemi.todayquote.presentation.author.composable

import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import ir.bahmanghasemi.todayquote.R
import ir.bahmanghasemi.todayquote.common.data.util.Shape
import ir.bahmanghasemi.todayquote.common.presentation.util.Extension.shimmerEffect
import ir.bahmanghasemi.todayquote.presentation.author.AuthorViewModel

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun AuthorsScreen(
    viewModel: AuthorViewModel = hiltViewModel<AuthorViewModel>()
) {
    val context = LocalContext.current
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var searchText by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            text = "Popular Authors",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Black
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
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
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            )
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            state.authors?.let { authors ->
                items(authors, key = { it.id }) { author ->
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.Start)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            AsyncImage(
                                modifier = Modifier.size(96.dp)
                                    .padding(top = 8.dp, end = 16.dp),
                                model = Shape.random(),
                                contentDescription = "Shape",
                            )
                            Text(
                                modifier = Modifier.padding(start = 8.dp, bottom = 16.dp),
                                text = author.name.first().uppercase(),
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.displayLarge
                            )
                        }
                        Text(modifier = Modifier.weight(1f), text = author.name)
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

