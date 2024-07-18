package ru.android.grokhotovapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.android.grokhotovapp.domain.model.Category
import ru.android.grokhotovapp.domain.model.ProductInfo

interface CloudRepository {
    fun getCategories(): Flow<List<Category>>
    fun getSlugsProducts(slug: String): Flow<List<String>>
    fun getProduct(slug: String): Flow<ProductInfo>
}