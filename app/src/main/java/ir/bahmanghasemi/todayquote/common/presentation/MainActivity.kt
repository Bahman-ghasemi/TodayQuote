package ir.bahmanghasemi.todayquote.common.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import ir.bahmanghasemi.todayquote.R
import ir.bahmanghasemi.todayquote.common.presentation.ui.theme.TodayQuoteTheme
import ir.bahmanghasemi.todayquote.common.presentation.util.AuthorNavType
import ir.bahmanghasemi.todayquote.common.presentation.util.UIExtension.NavigationBarItem
import ir.bahmanghasemi.todayquote.common.presentation.util.navigation.AuthorDetailRoute
import ir.bahmanghasemi.todayquote.common.presentation.util.navigation.AuthorRoute
import ir.bahmanghasemi.todayquote.common.presentation.util.navigation.DailyRoute
import ir.bahmanghasemi.todayquote.common.presentation.util.navigation.FavoriteRoute
import ir.bahmanghasemi.todayquote.common.presentation.util.navigation.NotificationRoute
import ir.bahmanghasemi.todayquote.common.presentation.util.navigation.SplashRoute
import ir.bahmanghasemi.todayquote.domain.model.Author
import ir.bahmanghasemi.todayquote.presentation.author.composable.AuthorScreen
import ir.bahmanghasemi.todayquote.presentation.author.composable.AuthorsScreen
import ir.bahmanghasemi.todayquote.presentation.daily_quote.composable.DailyQuoteScreen
import ir.bahmanghasemi.todayquote.presentation.favorite.composable.FavoriteScreen
import ir.bahmanghasemi.todayquote.presentation.notification.composable.NotificationScreen
import ir.bahmanghasemi.todayquote.presentation.splash.compsable.SplashScreen
import kotlin.reflect.typeOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodayQuoteTheme {
                val navController = rememberNavController()
                val backStackEntry = navController.currentBackStackEntryAsState()
                val currentDestination = backStackEntry.value?.destination?.route

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    bottomBar = {
                        if (currentDestination != SplashRoute.serializer().descriptor.serialName)
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
                    if (currentDestination != DailyRoute.serializer().descriptor.serialName)
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
                    if (currentDestination != AuthorRoute.serializer().descriptor.serialName)
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
                    if (currentDestination != FavoriteRoute.serializer().descriptor.serialName)
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
                    if (currentDestination != NotificationRoute.serializer().descriptor.serialName)
                        navController.navigate(NotificationRoute) {
                            popUpTo<DailyRoute>()
                            launchSingleTop = true
                        }
                }
            )

        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun AppNavigation(paddingValues: PaddingValues, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        SharedTransitionLayout(modifier = Modifier.fillMaxSize()) {
            NavHost(navController = navController, startDestination = SplashRoute) {

                composable<SplashRoute> {
                    SplashScreen {
                        navController.navigate(DailyRoute) {
                            popUpTo<DailyRoute>()
                            launchSingleTop = true
                        }
                    }
                }

                composable<DailyRoute> {
                    DailyQuoteScreen()
                }

                composable<AuthorRoute> {
                    AuthorsScreen(
                        animatedVisibilityScope = this
                    ) { author ->
                        navController.navigate(AuthorDetailRoute(author))
                    }
                }

                composable<AuthorDetailRoute>(
                    typeMap = mapOf(
                        typeOf<Author>() to AuthorNavType.AuthorType
                    )
                ) {
                    val args = it.toRoute<AuthorDetailRoute>()
                    AuthorScreen(animatedVisibilityScope = this, author = args.author) {
                        navController.popBackStack()
                    }
                }

                composable<FavoriteRoute> {
                    FavoriteScreen(animatedVisibilityScope = this)
                }
                composable<NotificationRoute> {
                    NotificationScreen()
                }
            }
        }
    }
}