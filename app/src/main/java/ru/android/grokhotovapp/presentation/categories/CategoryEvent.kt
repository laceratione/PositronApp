package ru.android.grokhotovapp.presentation.categories

import ru.android.grokhotovapp.domain.model.Category
import ru.android.grokhotovapp.domain.model.ProductInfo

sealed class CategoryEvent {
    data class CategoryClicked(val category: Category) : CategoryEvent()
    object CategoryActionInvoked : CategoryEvent()
    object BackClicked: CategoryEvent()
}