package ir.bahmanghasemi.todayquote.common.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.bahmanghasemi.todayquote.R
import ir.bahmanghasemi.todayquote.common.presentation.ui.theme.TodayQuoteTheme
import ir.bahmanghasemi.todayquote.common.presentation.util.AuthorRoute
import ir.bahmanghasemi.todayquote.common.presentation.util.Extension.NavigationBarItem
import ir.bahmanghasemi.todayquote.common.presentation.util.DailyRoute
import ir.bahmanghasemi.todayquote.common.presentation.util.FavoriteRoute
import ir.bahmanghasemi.todayquote.common.presentation.util.NotificationRoute
import ir.bahmanghasemi.todayquote.presentation.author.composable.AuthorsScreen
import ir.bahmanghasemi.todayquote.presentation.daily_quote.QuoteViewModel
import ir.bahmanghasemi.todayquote.presentation.daily_quote.composable.DailyQuoteScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodayQuoteTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    bottomBar = {
                        BottomNavMenu(navController)
                    }) { innerPadding ->
                    AppNavigation(innerPadding, navController)
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun BottomNavMenu(navController: NavHostController = rememberNavController()) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry.value?.destination?.route

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RectangleShape
    ) {
        NavigationBar(
            Modifier
                .fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary,
        ) {
            NavigationBarItem(
                selected = (currentDestination == DailyRoute.serializer().descriptor.serialName),
                selectedIcon = R.drawable.quote_fill,
                unselectedIcon = R.drawable.quote,
                label = "Daily Quote",
                onclick = {
                    navController.navigate(DailyRoute) {
                        popUpTo<DailyRoute>()
                        launchSingleTop = true
                    }
                }
            )

            NavigationBarItem(
                selected = (currentDestination == AuthorRoute.serializer().descriptor.serialName),
                selectedIcon = R.drawable.document_fill,
                unselectedIcon = R.drawable.document,
                label = "Authors",
                onclick = {
                    navController.navigate(AuthorRoute) {
                        popUpTo<DailyRoute>()
                        launchSingleTop = true
                    }
                }
            )

            NavigationBarItem(
                selected = (currentDestination == FavoriteRoute.serializer().descriptor.serialName),
                selectedIcon = R.drawable.heart_fill,
                unselectedIcon = R.drawable.heart,
                label = "Favorites",
                onclick = {
                    navController.navigate(FavoriteRoute) {
                        popUpTo<DailyRoute>()
                        launchSingleTop = true
                    }
                }
            )

            NavigationBarItem(
                selected = (currentDestination == NotificationRoute.serializer().descriptor.serialName),
                selectedIcon = R.drawable.notification_fill,
                unselectedIcon = R.drawable.notification,
                label = "Notification",
                onclick = {
                    navController.navigate(NotificationRoute) {
                        popUpTo<DailyRoute>()
                        launchSingleTop = true
                    }
                }
            )

        }
    }
}

@Composable
private fun AppNavigation(paddingValues: PaddingValues, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        NavHost(navController = navController, startDestination = DailyRoute) {
            composable<DailyRoute> {
                DailyQuoteScreen()
            }
            composable<AuthorRoute> {
                AuthorsScreen()
            }
            composable<FavoriteRoute> {
                // ProfileScreen
            }
            composable<NotificationRoute> {
                // Notification Setting Screen
            }
        }
    }
}