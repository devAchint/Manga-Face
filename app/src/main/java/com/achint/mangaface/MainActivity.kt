package com.achint.mangaface

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.achint.mangaface.ui.navigation.AppNavHost
import com.achint.mangaface.ui.navigation.Face
import com.achint.mangaface.ui.navigation.Manga
import com.achint.mangaface.ui.screens.signin.UserViewModel
import com.achint.mangaface.ui.theme.BottomBarBackground
import com.achint.mangaface.ui.theme.LightTextColor
import com.achint.mangaface.ui.theme.MangaFaceTheme
import com.achint.mangaface.ui.theme.nunFontFamily
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            viewModel.isUserSignedIn.value == null
        }
        enableEdgeToEdge()
        setContent {
            MangaFaceTheme(darkTheme = false) {
                val navController = rememberNavController()
                val bottomBarScreens = listOf(Manga::class.qualifiedName, Face::class.qualifiedName)
                val bottomBarRoutes = listOf(Manga, Face)
                val currentScreen =
                    navController.currentBackStackEntryAsState().value?.destination?.route
                var selectedIndex = bottomBarScreens.indexOf(currentScreen).takeIf { it != -1 } ?: 0

                Scaffold(
                    bottomBar = {
                        if (bottomBarScreens.contains(currentScreen)) {
                            BottomBar(
                                selectedIndex = selectedIndex,
                                onItemChange = {
                                    selectedIndex = it
                                    navController.navigate(bottomBarRoutes[it]) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->

                    val signInState = viewModel.isUserSignedIn.collectAsState().value
                    signInState?.let {
                        AppNavHost(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController,
                            userSignedIn = signInState,
                        )
                    }

                }
            }
        }
    }
}

data class BottomNavItem(
    val title: String,
    val icon: Int
)

@Composable
fun BottomBar(selectedIndex: Int, onItemChange: (Int) -> Unit) {

    val items = listOf(
        BottomNavItem("Manga", R.drawable.books_icon),
        BottomNavItem("Face", R.drawable.camera_icon)
    )
    NavigationBar(
        containerColor = BottomBarBackground
    ) {
        items.forEachIndexed { index, item ->
            val fontWeight = if (selectedIndex == index) FontWeight.Bold else FontWeight.Normal
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title, fontFamily = nunFontFamily, fontWeight = fontWeight) },
                selected = selectedIndex == index,
                onClick = { onItemChange(index) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = LightTextColor,
                    unselectedTextColor = LightTextColor,
                    selectedTextColor = Color.White,
                    indicatorColor = Color(0xff464157)
                )
            )
        }
    }
}


