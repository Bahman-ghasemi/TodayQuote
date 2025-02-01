package ir.bahmanghasemi.todayquote.presentation.splash.compsable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ir.bahmanghasemi.todayquote.R
import ir.bahmanghasemi.todayquote.common.presentation.ui.theme.LocalFontFamilyPtSerifCaption
import kotlinx.coroutines.delay

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SplashScreen(onNavigate: () -> Unit = {}) {
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            model = R.drawable.abstract_shape_splash,
            contentDescription = "splash",
            contentScale = ContentScale.FillWidth
        )

        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 48.dp, bottom = 96.dp),
            text = "Quotes",
            style = MaterialTheme.typography.displaySmall,
            fontFamily = LocalFontFamilyPtSerifCaption.current
        )
    }

    LaunchedEffect(true) {
        delay(2500)
        onNavigate()
    }
}