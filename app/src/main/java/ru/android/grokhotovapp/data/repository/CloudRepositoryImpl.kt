package ru.android.grokhotovapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.android.grokhotovapp.data.api.RetrofitAPI
import ru.android.grokhotovapp.data.model.mapToDomain
import ru.android.grokhotovapp.domain.model.Category
import ru.android.grokhotovapp.domain.model.ProductInfo
import ru.android.grokhotovapp.domain.repository.CloudRepository

class CloudRepositoryImpl(private val retrofitAPI: RetrofitAPI) : CloudRepository {
    override fun getCategories(): Flow<List<Category>> = flow {
        emit(retrofitAPI.getCategories().map { it.mapToDomain() })
    }

    override fun getSlugsProducts(slug: String): Flow<List<String>> = flow {
        emit(retrofitAPI.getListProducts(slug).map { it.slug })
    }

    override fun getProduct(slug: String): Flow<ProductInfo> = flow {
        emit(retrofitAPI.getProduct(slug).mapToDomain())
    }


}