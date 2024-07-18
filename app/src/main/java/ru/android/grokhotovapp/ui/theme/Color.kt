package ru.android.grokhotovapp.ui.theme

import androidx.compose.ui.graphics.Color

data class Colors(
    val colorPrimary: Color,
    val textColorPrimary: Color,
    val windowBackground: Color,
    val headerTextColor: Color,
    val primaryTextColor: Color,
    val primaryTintColor: Color,
    val discountColor: Color,
    val dividerColor: Color,

)

val lightPalette = Colors(
    colorPrimary = Color(0xFF0AB66B),
    textColorPrimary = Color.White,
    windowBackground = Color.White,
    headerTextColor = Color(0xFF444444),
    primaryTextColor = Color(0xFF45424E),
    primaryTintColor = Color(0xFFB1B5BE),
    discountColor = Color(0xFFF34129),
    dividerColor = Color(0xFFF4F5F7)
)