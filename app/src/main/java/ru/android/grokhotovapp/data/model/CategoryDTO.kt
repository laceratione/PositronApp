package ru.android.grokhotovapp.data.model

import com.google.gson.annotations.SerializedName
import ru.android.grokhotovapp.domain.model.Category

data class CategoryDTO(
    @SerializedName("title") val title: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("subCategories") val subCategories: List<Category>,
    @SerializedName("icon") val icon: String,
)

fun CategoryDTO.mapToDomain() = Category(
    title = title,
    slug = slug,
    subCategories = subCategories,
    icon = icon
)