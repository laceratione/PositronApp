package ru.android.grokhotovapp.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.android.grokhotovapp.domain.model.Category
import ru.android.grokhotovapp.domain.repository.CloudRepository

class GetCategories(
    private val cloudRepository: CloudRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke():
            Flow<List<Category>> = withContext(defaultDispatcher) {
        val items = cloudRepository.getCategories()
        items
    }
}