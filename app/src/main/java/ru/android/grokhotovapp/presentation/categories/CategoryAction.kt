package ru.android.grokhotovapp.presentation.categories

sealed class CategoryAction {
    object OpenSubCategory: CategoryAction()
    data class OpenListProducts(val slug: String): CategoryAction()
    object None: CategoryAction()
}