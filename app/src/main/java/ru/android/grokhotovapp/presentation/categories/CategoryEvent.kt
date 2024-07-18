package ru.android.grokhotovapp.presentation.categories

import ru.android.grokhotovapp.domain.model.Category
import ru.android.grokhotovapp.domain.model.ProductInfo

sealed class CategoryEvent {
    data class CategoryClicked(val category: Category) : CategoryEvent()
    object CategoryActionInvoked : CategoryEvent()
    object ListProductsClicked : CategoryEvent()
    data class ProductInfoClicked(val info: ProductInfo) : CategoryEvent()
    object BackClicked: CategoryEvent()
}