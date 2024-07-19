package ru.android.grokhotovapp.domain.model

import androidx.compose.ui.graphics.ImageBitmap

data class ProductInfo(
    val title: String,
    val sku: Int,
    val description: String,
    val price: Int = 0,
    val priceOld: Int = 0,
    val sizeDiscount: Double = 0.0,
    val countAvailable: Int = 0,
    val images: List<String>,
    val productSlug: String = ""
)