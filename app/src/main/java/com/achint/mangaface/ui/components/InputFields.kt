package com.achint.mangaface.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.achint.mangaface.R
import com.achint.mangaface.ui.theme.ErrorColor
import com.achint.mangaface.ui.theme.LightTextColor

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    error: Boolean? = false
) {
    Column {
        OutlinedTextField(
            value = text, modifier = modifier
                .fillMaxWidth(),
            onValueChange = onValueChange,
            label = {
                Text(text = placeholder, color = LightTextColor)
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
            )
        )
        if (error == true) {
            Text(text = "That's not a valid email", color = ErrorColor, fontSize = 12.sp)
        }
    }

}

@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    error: Boolean? = false,
    passwordVisible: Boolean,
    passwordVisibleChange: (Boolean) -> Unit
) {
    Column {
        OutlinedTextField(
            value = text,
            modifier = modifier
                .fillMaxWidth(),
            onValueChange = onValueChange,
            label = {
                Text(text = placeholder, color = LightTextColor)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
            ),
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon =
                    if (passwordVisible) R.drawable.visibility else R.drawable.visibility_off
                val description = if (passwordVisible) "Hide password" else "Show password"
                IconButton(onClick = { passwordVisibleChange(!passwordVisible) }) {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = description,
                        tint = LightTextColor
                    )
                }
            },
        )
        if (error == true) {
            Text(
                text = "The password must be at least 6 characters",
                color = ErrorColor,
                fontSize = 12.sp
            )
        }
    }
}