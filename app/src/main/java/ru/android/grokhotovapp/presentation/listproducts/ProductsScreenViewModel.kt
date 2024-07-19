package ru.android.grokhotovapp.presentation.listproducts

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.android.grokhotovapp.domain.model.ProductInfo
import ru.android.grokhotovapp.domain.usecase.GetProduct
import ru.android.grokhotovapp.domain.usecase.GetSlugsProducts
import javax.inject.Inject

@HiltViewModel
class ProductsScreenViewModel @Inject constructor(
    val getSlugsProductsUseCase: GetSlugsProducts,
    val getProductUseCase: GetProduct,
    handle: SavedStateHandle
) : ViewModel() {
    private val _listProducts: MutableStateFlow<List<ProductInfo>> = MutableStateFlow(emptyList())
    val listProducts: StateFlow<List<ProductInfo>> = _listProducts.asStateFlow()

    private val _isListProductLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isListProductLoading: StateFlow<Boolean> = _isListProductLoading.asStateFlow()

    private val _viewAction: MutableStateFlow<ProductsScreenAction> =
        MutableStateFlow(ProductsScreenAction.None)
    val viewAction: StateFlow<ProductsScreenAction> = _viewAction.asStateFlow()

    init {
        val slug = handle.get<String>("slug").orEmpty()
        viewModelScope.launch { getListProducts(slug) }
    }

    fun obtainEvent(event: ProductsScreenEvent) {
        when (event) {
            is ProductsScreenEvent.ProductInfoClicked -> productInfoAction(event.slug)
            ProductsScreenEvent.AddToFavoritesClicked -> addToFavoritesClicked()
            ProductsScreenEvent.ProductsScreenActionInvoked -> productsScreenActionInvoked()
            else -> {}
        }

    }

    private fun productsScreenActionInvoked() {
        _viewAction.value = ProductsScreenAction.None
    }

    private fun addToFavoritesClicked() {
        TODO("Not yet implemented")
    }

    private fun productInfoAction(slug: String) {
        _viewAction.value = ProductsScreenAction.OpenProductInfo(slug)
    }

    private suspend fun getListProducts(categorySlug: String) = coroutineScope {
        var slugs = emptyList<String>()
        val products = mutableListOf<ProductInfo>()

        _isListProductLoading.value = true
        getSlugsProductsUseCase(categorySlug)
            .flowOn(Dispatchers.IO)
            .catch { error -> Log.d("myLogs", error.message.toString()) }
            .collect {
                slugs = it
            }

        if (slugs.isNotEmpty()) {
            slugs.forEach {
                getProductUseCase(it)
                    .flowOn(Dispatchers.IO)
                    .catch { error -> Log.d("myLogs", error.message.toString()) }
                    .collect {
                        products.add(it)
                    }
            }
            _listProducts.value = products
        }
        _isListProductLoading.value = false
    }
}