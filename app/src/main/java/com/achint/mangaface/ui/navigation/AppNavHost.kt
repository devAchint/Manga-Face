package com.achint.mangaface.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.achint.mangaface.ui.screens.AuthViewModel
import com.achint.mangaface.ui.screens.SignInScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = SignIn) {
        composable<SignIn> {
            val authViewModel: AuthViewModel = hiltViewModel()
            SignInScreen(
                signIn = { email, password -> authViewModel.signIn(email, password) }
            )
        }
    }
}