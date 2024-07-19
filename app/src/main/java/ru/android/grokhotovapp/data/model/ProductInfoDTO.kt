package ru.android.grokhotovapp.data.model

import com.google.gson.annotations.SerializedName
import ru.android.grokhotovapp.domain.model.ProductInfo

data class ProductInfoDTO(
    @SerializedName("title") val title: String,
    @SerializedName("sku") val sku: Int,
    @SerializedName("description") val description: String,
    @SerializedName("purchase") val purchase: Purchase,
    @SerializedName("images") val images: List<ImagesProduct>
)

data class Purchase(
    @SerializedName("price") val price: Int,
    @SerializedName("price_old") val priceOld: Int,
    @SerializedName("size_discount") val sizeDiscount: Double,
    @SerializedName("count_available") val countAvailable: Int,
    @SerializedName("product_slug") val productSlug: String
)

data class ImagesProduct(
    @SerializedName("original") val original: String
)

fun ProductInfoDTO.mapToDomain() = ProductInfo(
    title = title,
    sku = sku,
    description = description,
    price = purchase.price,
    priceOld = purchase.priceOld,
    sizeDiscount = purchase.sizeDiscount,
    images = images.map { it.original },
    countAvailable = purchase.countAvailable,
    productSlug = purchase.productSlug

)
