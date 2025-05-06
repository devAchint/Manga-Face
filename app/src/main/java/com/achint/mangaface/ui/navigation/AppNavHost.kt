package com.achint.mangaface.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.achint.mangaface.ui.screens.face.FaceScreenRoot
import com.achint.mangaface.ui.screens.manga.MangaScreenRoot
import com.achint.mangaface.ui.screens.mangaDetail.MangaDetailScreenRoot
import com.achint.mangaface.ui.screens.signin.SignInScreenRoot

@Composable
fun AppNavHost(
    modifier: Modifier=Modifier,
    navController: NavHostController,
    userSignedIn: Boolean = false,
    setTitle: (String?) -> Unit,
) {
    NavHost(modifier = modifier,
        navController = navController,
        startDestination = if (userSignedIn) Manga else SignIn) {
        composable<SignIn> {
            setTitle(null)
            SignInScreenRoot(
                navigateToHome = {
                    navController.navigate(Manga)
                }
            )
        }

        composable<Manga> {
            setTitle("Manga")
            MangaScreenRoot(
                navigateToMangaDetail = {
                    navController.navigate(MangaDetail)
                }
            )
        }
        composable<MangaDetail> {
            setTitle(null)
            MangaDetailScreenRoot()
        }

        composable<Face> {
            setTitle("Face")
            FaceScreenRoot()
        }
    }
}