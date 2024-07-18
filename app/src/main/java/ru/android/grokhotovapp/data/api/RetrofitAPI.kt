package ru.android.grokhotovapp.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import ru.android.grokhotovapp.data.model.CategoryDTO
import ru.android.grokhotovapp.data.model.ProductInfoDTO
import ru.android.grokhotovapp.data.model.SlugDTO

interface RetrofitAPI {
    @GET("/categories/-/catalog")
    suspend fun getCategories(): List<CategoryDTO>

    @GET("/products/{categorySlug}/catalog")
    suspend fun getListProducts(@Path("categorySlug") slug: String): List<SlugDTO>

    @GET("/products/{productSlug}")
    suspend fun getProduct(@Path("productSlug") slug: String): ProductInfoDTO
}