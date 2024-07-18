package ru.android.grokhotovapp.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.android.grokhotovapp.domain.model.ProductInfo
import ru.android.grokhotovapp.domain.repository.CloudRepository

class GetProduct(
    private val cloudRepository: CloudRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke(slug: String):
            Flow<ProductInfo> = withContext(defaultDispatcher) {
        val items = cloudRepository.getProduct(slug)
        items
    }
}