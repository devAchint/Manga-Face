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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.achint.mangaface.domain.model.MangaModel
import com.achint.mangaface.ui.components.Toolbar
import com.achint.mangaface.ui.navigation.AppNavHost
import com.achint.mangaface.ui.navigation.Face
import com.achint.mangaface.ui.navigation.Manga
import com.achint.mangaface.ui.screens.signin.UserViewModel
import com.achint.mangaface.ui.theme.LightTextColor
import com.achint.mangaface.ui.theme.MangaFaceTheme
import com.achint.mangaface.ui.theme.PrimaryColor
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
                var title by rememberSaveable {
                    mutableStateOf<String?>(null)
                }
                val bottomBarScreens = listOf(Manga::class.qualifiedName, Face::class.qualifiedName)
                val bottomBarRoutes = listOf(Manga, Face)
                val currentScreen =
                    navController.currentBackStackEntryAsState().value?.destination?.route
                var selectedIndex = bottomBarScreens.indexOf(currentScreen).takeIf { it != -1 } ?: 0

                Scaffold(
                    topBar = {
                        title?.let {
                            Toolbar(title = it)
                        }
                    },
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
                    val manga = MangaModel(
                        id = 1,
                        title = "Demon X Angel, Can’t Get Along!",
                        sub_title = "Demon X Angel Can't Get Along! ; 恶魔X天使 不能友好相处",
                        status = "ongoing",
                        thumb = "https://usc1.contabostorage.com/scraper/mangas/65b65c32bf13b1bd72cec0c7/thumb.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=c10e9464b360c31ce8abea9b266076f6%2F20250506%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250506T071117Z&X-Amz-Expires=86400&X-Amz-SignedHeaders=host&X-Amz-Signature=27decbf9c9623cea6091b23ed3c8523a9a9d4ebeea864b82d2155256f266b7b6",
                        summary = "Demons and angels are two opposite races. As the times change, they are tired of fighting. In order to coexist peacefully, they decided to start a marriage plan! The plan has a one-year observation period. If the devil and angel still cannot get along well, the marriage will be cancelled. As a result, Jiacheng devil and Yu Shanshan angel were “luckily” selected by lottery as marriage partners, and started a “happy” cohabitation life with the destination of divorce~",
                        authors = listOf("Filo Cat ; 菲洛猫"),
                        genres = listOf("Fantasy", "Romance", "Comedy", "Manhua"),
                        nsfw = false,
                        type = "china",
                        total_chapter = 145
                    )

                    // MangaDetailScreenRoot(manga = manga,modifier = Modifier.padding(innerPadding))
                    //MangaScreenRoot()
                    // SignInScreenRoot()
                    val signInState = viewModel.isUserSignedIn.collectAsState().value
                    signInState?.let {
                        AppNavHost(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController,
                            userSignedIn = signInState,
                            setTitle = {
                                title = it
                            }
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
        containerColor = Color.White
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
                    selectedIconColor = Color.Black,
                    unselectedIconColor = LightTextColor,
                    unselectedTextColor = LightTextColor,
                    selectedTextColor = Color.Black,
                    indicatorColor = PrimaryColor.copy(alpha = 0.2f)
                )
            )
        }
    }
}


