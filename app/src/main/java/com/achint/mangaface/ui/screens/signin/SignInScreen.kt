package com.achint.mangaface.ui.screens.signin

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.achint.mangaface.ui.components.InputField
import com.achint.mangaface.ui.components.LoadingButton

@Composable
fun SignInScreenRoot(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit = {},
    viewModel: AuthViewModel = hiltViewModel()
) {
    SignInScreen(
        authUiState = viewModel.authUiState.collectAsState().value,
        onEmailChange = viewModel::setEmail,
        onPasswordChange = viewModel::setPassword,
        signIn = viewModel::authenticate,
        navigateToHome = navigateToHome,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    authUiState: AuthUiState = AuthUiState(),
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    navigateToHome: () -> Unit = {},
    signIn: () -> Unit = {  }
) {
    LaunchedEffect(authUiState.signInState) {
        when(authUiState.signInState) {
            SignInStates.InvalidCredentials -> {

            }
            SignInStates.Success -> {
                navigateToHome()
            }
            SignInStates.Fail -> {

            }
            null -> {}
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Sign In") })
        }
    ) { innerPadding ->
        val context = LocalContext.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {

            InputField(
                text = authUiState.email,
                placeholder = "Enter email",
                onValueChange = onEmailChange
            )
            Spacer(modifier = Modifier.height(16.dp))
            InputField(
                text = authUiState.password,
                placeholder = "Enter password",
                onValueChange = onPasswordChange
            )
            Spacer(modifier = Modifier.height(16.dp))
            LoadingButton(text = "Sign In", isLoading = authUiState.isLoading) {
                if (!authUiState.isValidEmail) {
                    Toast.makeText(context, "Email is not valid", Toast.LENGTH_SHORT).show()
                    return@LoadingButton
                }
                if (!authUiState.isValidPassword) {
                    Toast.makeText(context, "Password is not valid", Toast.LENGTH_SHORT).show()
                    return@LoadingButton
                }
                signIn()
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SignInPreview() {
    SignInScreen()
}