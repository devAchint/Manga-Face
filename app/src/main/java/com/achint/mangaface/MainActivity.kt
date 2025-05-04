package com.achint.mangaface

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
                val bottomBarScreens = listOf(Manga, Face)
                val currentScreen =
                    navController.currentBackStackEntryAsState().value?.destination
                var selectedIndex by rememberSaveable {
                    mutableStateOf(0)
                }
                val viewModel: AuthViewModel = hiltViewModel()
                Scaffold(
                    bottomBar = {
//                        BottomBar(
//                            selectedIndex = selectedIndex,
//                            onItemChange = {
//                                selectedIndex = it
//                                navController.navigate(Manga)
//                            }
//                        )
                    }
                ) {
                    AppNavHost(
                        navController = navController,
                        userSignedIn = viewModel.authUiState.collectAsState().value.signInState == SignInStates.Success
                    )
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
        BottomNavItem("Home", Icons.Filled.AccountCircle),
        BottomNavItem("Manga", Icons.Filled.Face)
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


