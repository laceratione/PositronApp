package ru.android.grokhotovapp.common

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun PriceRow(price: Int, color: Color) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(
            text = "$price ₽",
            color = color,
            fontSize = 30.sp
        )
        Text(text = "/шт", color = color, fontSize = 23.sp)
    }
}


