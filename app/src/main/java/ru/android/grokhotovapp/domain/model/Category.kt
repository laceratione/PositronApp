package ru.android.grokhotovapp.domain.model

data class Category(
    val title: String,
    val slug: String,
    val subCategories: List<Category>,
    val icon: String,
    var isRoot: Boolean = false
)
