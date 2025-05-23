package com.achint.mangaface.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.achint.mangaface.ui.theme.ButtonColor

@Preview
@Composable
fun CommonButton(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isEnabled: Boolean = false,
    text: String = "Button",
    onClick: () -> Unit = {},
) {
    Button(
        onClick = onClick,
        enabled = isEnabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonColor,
            disabledContainerColor = Color(0xff4a4645)
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp),
                strokeWidth = 2.5.dp,
                color = Color.White
            )
        } else {
            Text(text = text, fontSize = 16.sp)
        }
    }
}

