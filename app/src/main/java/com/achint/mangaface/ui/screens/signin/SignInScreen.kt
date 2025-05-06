package com.achint.mangaface.ui.screens.signin

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.achint.mangaface.R
import com.achint.mangaface.ui.components.InputField
import com.achint.mangaface.ui.components.LoadingButton
import com.achint.mangaface.ui.components.PasswordField
import com.achint.mangaface.ui.theme.LightTextColor
import com.achint.mangaface.ui.theme.nunFontFamily

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

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    authUiState: AuthUiState = AuthUiState(),
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    navigateToHome: () -> Unit = {},
    signIn: () -> Unit = { }
) {
    val context = LocalContext.current

    LaunchedEffect(authUiState.signInState) {
        authUiState.signInState?.let {
            when (it) {
                SignInStates.InvalidCredentials -> {
                    Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                }

                SignInStates.Success -> {
                    Toast.makeText(context, "Successfully Signed In", Toast.LENGTH_SHORT).show()
                    navigateToHome()
                }

                SignInStates.Fail -> {
                    Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.logo), contentDescription = null,
            modifier = Modifier
                .padding(top = 80.dp)
                .size(72.dp)
                .clip(RoundedCornerShape(20.dp))
        )
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "Login",
            fontSize = 32.sp,
            fontFamily = nunFontFamily,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Enter your email and password", color = LightTextColor)
        Spacer(modifier = Modifier.height(20.dp))
        InputField(
            text = authUiState.email,
            placeholder = "Enter email",
            onValueChange = onEmailChange,
            icon = Icons.Filled.Email,
            error = authUiState.isValidEmail?.not()
        )
        Spacer(modifier = Modifier.height(16.dp))
        PasswordField(
            text = authUiState.password,
            placeholder = "Enter password",
            onValueChange = onPasswordChange,
            passwordVisible = passwordVisible,
            error = authUiState.isValidPassword?.not(),
            passwordVisibleChange = {
                passwordVisible = it
            }
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Forget Password!",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )
        Spacer(modifier = Modifier.height(20.dp))
        LoadingButton(text = "Sign In", isLoading = authUiState.isLoading) {
            signIn()
        }
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SignInPreview() {
    SignInScreen()
}