package com.achint.mangaface.ui.screens.signin

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.achint.mangaface.R
import com.achint.mangaface.ui.components.InputField
import com.achint.mangaface.ui.components.CommonButton
import com.achint.mangaface.ui.components.PasswordField
import com.achint.mangaface.ui.theme.Background
import com.achint.mangaface.ui.theme.LoginCardColor

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentAlignment = Alignment.Center
    ) {
        Row(modifier = Modifier
            .padding(8.dp)
            .align(Alignment.TopStart)) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = "Close", tint = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Sign In", color = Color.White)
        }
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = LoginCardColor
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Zenithra", color = Color.White, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Welcome Back", color = Color.White, fontSize = 32.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Please enter your details to sign in",
                    color = Color.LightGray,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Icon(
                        painter = painterResource(R.drawable.google_icon),
                        contentDescription = "Email",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                            .padding(6.dp),
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        painter = painterResource(R.drawable.apple_icon),
                        contentDescription = "Email",
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                            .padding(6.dp),
                        tint = Color.White
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        thickness = 1.dp,
                        color = Color.Gray
                    )

                    Text(
                        text = "OR",
                        modifier = Modifier.padding(horizontal = 4.dp),
                        fontSize = 18.sp,
                        color = Color.White
                    )

                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        thickness = 1.dp,
                        color = Color.Gray
                    )
                }
                InputField(
                    text = authUiState.email,
                    onValueChange = onEmailChange,
                    placeholder = "Your Email Address",
                    error = authUiState.isValidEmail?.not()
                )
                Spacer(modifier = Modifier.height(16.dp))
                PasswordField(
                    text = authUiState.password,
                    placeholder = "Password",
                    onValueChange = onPasswordChange,
                    passwordVisible = passwordVisible,
                    error = authUiState.isValidPassword?.not(),
                    passwordVisibleChange = {
                        passwordVisible = it
                    }
                )
                Text(
                    text = "Forget Password!",
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .fillMaxWidth()
                        .clickable {
                            Toast.makeText(
                                context,
                                "Functionality Not Available",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                    textAlign = TextAlign.End,
                    color = Color(0xFF87CEEB),
                    style = androidx.compose.ui.text.TextStyle(textDecoration = TextDecoration.Underline)
                )
                CommonButton(
                    text = "Sign In",
                    isLoading = authUiState.isLoading,
                    isEnabled = authUiState.isValidEmail == true && authUiState.isValidPassword == true
                ) {
                    signIn()
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    buildAnnotatedString {
                        append("Don't have an account? ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Signup")
                        }
                    },
                    color = Color.LightGray,
                    fontSize = 16.sp
                )

            }
        }
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SignInPreview() {
    SignInScreen()
}