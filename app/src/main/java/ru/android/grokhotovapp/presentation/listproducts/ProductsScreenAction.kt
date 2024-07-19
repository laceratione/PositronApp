package ru.android.grokhotovapp.presentation.listproducts

sealed class ProductsScreenAction {
    data class OpenProductInfo(val slug: String): ProductsScreenAction()
    object None: ProductsScreenAction()
}