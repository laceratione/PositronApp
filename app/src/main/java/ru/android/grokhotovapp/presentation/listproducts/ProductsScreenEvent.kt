package ru.android.grokhotovapp.presentation.listproducts

sealed class ProductsScreenEvent {
    object ProductsScreenActionInvoked: ProductsScreenEvent()
    data class ProductInfoClicked(val slug: String): ProductsScreenEvent()
    object AddToFavoritesClicked: ProductsScreenEvent()
}