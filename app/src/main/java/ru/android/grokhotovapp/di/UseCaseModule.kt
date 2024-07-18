package ru.android.grokhotovapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.android.grokhotovapp.domain.repository.CloudRepository
import ru.android.grokhotovapp.domain.usecase.GetCategories
import ru.android.grokhotovapp.domain.usecase.GetProduct
import ru.android.grokhotovapp.domain.usecase.GetSlugsProducts

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    fun provideGetDataDishTypes(cloudRepository: CloudRepository): GetCategories {
        return GetCategories(cloudRepository)
    }

    @Provides
    fun provideGetSlugsProducts(cloudRepository: CloudRepository): GetSlugsProducts {
        return GetSlugsProducts(cloudRepository)
    }

    @Provides
    fun provideGetProduct(cloudRepository: CloudRepository): GetProduct{
        return GetProduct(cloudRepository)
    }
}