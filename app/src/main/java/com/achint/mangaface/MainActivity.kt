package com.achint.mangaface

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.achint.mangaface.ui.navigation.AppNavHost
import com.achint.mangaface.ui.navigation.Face
import com.achint.mangaface.ui.navigation.Manga
import com.achint.mangaface.ui.screens.signin.AuthViewModel
import com.achint.mangaface.ui.screens.signin.SignInStates
import com.achint.mangaface.ui.theme.MangaFaceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MangaFaceTheme(darkTheme = false) {
                val navController = rememberNavController()
                val bottomBarScreens = listOf(Manga::class.qualifiedName, Face::class.qualifiedName)
                val bottomBarRoutes = listOf(Manga, Face)
                val currentScreen =
                    navController.currentBackStackEntryAsState().value?.destination?.route
                var selectedIndex = bottomBarScreens.indexOf(currentScreen).takeIf { it != -1 } ?: 0

                val viewModel: AuthViewModel = hiltViewModel()
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
                ) {
                    val signInState = viewModel.authUiState.collectAsState().value.signInState

                    when (signInState) {
                        null -> {
                            // Show splash/loading screen while state is being determined
                            Box(modifier = Modifier.padding(it).fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }

                        else -> {
                            AppNavHost(
                                navController = navController,
                                userSignedIn = signInState == SignInStates.Success
                            )
                        }
                    }
                }
            }
        }
    }
}

data class BottomNavItem(
    val title: String,
    val icon: ImageVector
)

@Composable
fun BottomBar(selectedIndex: Int, onItemChange: (Int) -> Unit) {

    val items = listOf(
        BottomNavItem("Manga", Icons.Filled.AccountCircle),
        BottomNavItem("Face", Icons.Filled.Face)
    )
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = "") },
                label = { Text(item.title) },
                selected = selectedIndex == index,
                onClick = { onItemChange(index) }
            )
        }
    }
}


