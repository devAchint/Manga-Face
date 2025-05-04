package com.achint.mangaface.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.achint.mangaface.ui.screens.manga.MangaScreenRoot
import com.achint.mangaface.ui.screens.signin.SignInScreenRoot

@Composable
fun AppNavHost(navController: NavHostController, userSignedIn: Boolean = false) {
    NavHost(navController = navController, startDestination = if (userSignedIn) Manga else SignIn) {
        composable<SignIn> {
            SignInScreenRoot(
                navigateToHome = {
                    navController.navigate(Manga)
                }
            )
        }

        composable<Manga> {
            MangaScreenRoot()
        }
    }
}