package com.achint.mangaface.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.achint.mangaface.R
import com.achint.mangaface.ui.theme.ErrorColor
import com.achint.mangaface.ui.theme.LightTextColor

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
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
            shape = RoundedCornerShape(10.dp),
            leadingIcon = {
                Icon(imageVector = icon, contentDescription = "Email")
            },
            singleLine = true
        )
        if (error == true) {
            Text(text = "Email is not valid", color = ErrorColor, fontSize = 12.sp)
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
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Lock, contentDescription = "Password")
            },
            trailingIcon = {
                val icon =
                    if (passwordVisible) R.drawable.visibility else R.drawable.visibility_off
                val description = if (passwordVisible) "Hide password" else "Show password"
                IconButton(onClick = { passwordVisibleChange(!passwordVisible) }) {
                    Icon(painter = painterResource(icon), contentDescription = description)
                }
            },
        )
        if (error == true) {
            Text(text = "Password is not valid", color = ErrorColor, fontSize = 12.sp)
        }
    }
}