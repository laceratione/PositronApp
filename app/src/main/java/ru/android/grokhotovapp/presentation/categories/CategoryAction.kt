package ru.android.grokhotovapp.presentation.categories

sealed class CategoryAction {
    object OpenSubCategory: CategoryAction()
    object OpenListProducts: CategoryAction()
    object OpenProductInfo: CategoryAction()
    object None: CategoryAction()
}