package com.achint.mangaface.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.achint.mangaface.domain.model.MangaModel
import com.achint.mangaface.ui.screens.face.FaceScreenRoot
import com.achint.mangaface.ui.screens.manga.MangaScreenRoot
import com.achint.mangaface.ui.screens.mangaDetail.MangaDetailScreenRoot
import com.achint.mangaface.ui.screens.signin.SignInScreenRoot
import com.achint.mangaface.utils.CustomNavType
import kotlin.reflect.typeOf

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    userSignedIn: Boolean,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = if (userSignedIn) Manga else SignIn
    ) {
        composable<SignIn> {
            SignInScreenRoot(
                navigateToHome = {
                    navController.navigate(Manga)
                }
            )
        }

        composable<Manga> {
            MangaScreenRoot(
                navigateToMangaDetail = {
                    navController.navigate(MangaDetail(it))
                }
            )
        }
        composable<MangaDetail>(
            typeMap = mapOf(
                typeOf<MangaModel>() to CustomNavType.MangaType
            )
        ) {
            val arguments = it.toRoute<MangaDetail>()
            MangaDetailScreenRoot(
                manga = arguments.mangaModel,
                onBackPressed = { navController.popBackStack() }
            )
        }

        composable<Face> {
            FaceScreenRoot()
        }
    }
}