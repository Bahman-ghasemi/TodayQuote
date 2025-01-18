package ir.bahmanghasemi.todayquote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ir.bahmanghasemi.todayquote.common.presentation.ui.theme.TodayQuoteTheme
import ir.bahmanghasemi.todayquote.common.presentation.util.CategoryRoute
import ir.bahmanghasemi.todayquote.common.presentation.util.HomeRoute
import ir.bahmanghasemi.todayquote.common.presentation.util.ProfileRoute

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodayQuoteTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNavMenu(navController) }) { innerPadding ->
                    AppNavigation(Modifier.padding(innerPadding), navController)
                }
            }
        }
    }
}

@Composable
private fun BottomNavMenu(navController: NavHostController) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry.value?.destination?.route
    val selectedColor = MaterialTheme.colorScheme.primary
    val unselectedColor = Color.DarkGray

    Row(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color.LightGray)

    ) {
        NavBarItem(
            selected = (currentDestination == CategoryRoute.serializer().descriptor.serialName),
            resId = R.drawable.category,
            text = "Category",
            selectedColor = selectedColor,
            unselectedColor = unselectedColor,
            onClick = {
                navController.navigate(CategoryRoute) {
                    popUpTo(HomeRoute)
                    launchSingleTop = true
                }
            }
        )

        NavBarItem(
            selected = (currentDestination == HomeRoute.serializer().descriptor.serialName),
            resId = R.drawable.home,
            text = "Home",
            selectedColor = selectedColor,
            unselectedColor = unselectedColor,
            onClick = {
                navController.navigate(HomeRoute) {
                    popUpTo(HomeRoute)
                    launchSingleTop = true
                }
            }
        )

        NavBarItem(
            selected = (currentDestination == ProfileRoute.serializer().descriptor.serialName),
            resId = R.drawable.user,
            text = "Profile",
            selectedColor = selectedColor,
            unselectedColor = unselectedColor,
            onClick = {
                navController.navigate(ProfileRoute) {
                    popUpTo(HomeRoute)
                    launchSingleTop = true
                }
            }
        )
    }
}

@Composable
fun RowScope.NavBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    resId: Int,
    text: String?,
    selectedColor: Color,
    unselectedColor: Color
) {
    NavigationBarItem(
        selected = selected,
        icon = {
            Icon(
                painter = painterResource(resId),
                contentDescription = "Profile",
            )
        },
        label = {
            text?.let {
                Text(text = text, fontWeight = FontWeight.Bold)
            }
        },
        colors = NavigationBarItemColors(
            selectedIconColor = selectedColor,
            selectedTextColor = selectedColor,
            selectedIndicatorColor = Color.Transparent,
            disabledIconColor = Color.LightGray,
            disabledTextColor = Color.LightGray,
            unselectedIconColor = unselectedColor,
            unselectedTextColor = unselectedColor
        ),
        onClick = onClick
    )
}

@Composable
private fun AppNavigation(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(navController = navController, startDestination = HomeRoute) {
        composable<HomeRoute> {
            // HomeScreen
        }
        composable<CategoryRoute> {
            // CategoryScreen
        }
        composable<ProfileRoute> {
            // ProfileScreen
        }
    }
}