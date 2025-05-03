package com.achint.mangaface.ui.screens

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.achint.mangaface.ui.components.InputField
import com.achint.mangaface.ui.components.MyButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    signIn: (email: String, password: String) -> Unit = { _, _ -> }
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Sign In") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            var email by rememberSaveable { mutableStateOf("") }
            var password by rememberSaveable { mutableStateOf("") }

            InputField(
                text = email,
                placeholder = "Enter email",
                onValueChange = {
                    email = it
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            InputField(
                text = password,
                placeholder = "Enter password",
                onValueChange = {
                    password = it
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            MyButton(text = "Sign In") {
                signIn(email, password)
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SignInPreview() {
    SignInScreen()
}