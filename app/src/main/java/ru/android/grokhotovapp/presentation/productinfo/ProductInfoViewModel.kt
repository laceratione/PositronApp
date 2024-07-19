package ru.android.grokhotovapp.presentation.productinfo

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
import javax.inject.Inject

@HiltViewModel
class ProductInfoViewModel @Inject constructor(
    val getProductUseCase: GetProduct,
    handle: SavedStateHandle
) : ViewModel() {
    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private var _productInfo: MutableStateFlow<ProductInfo> =
        MutableStateFlow(ProductInfo("", 0, "", images = emptyList()))
    val productInfo: StateFlow<ProductInfo> = _productInfo.asStateFlow()

    init {
        val slug = handle.get<String>("slug").orEmpty()
        viewModelScope.launch { getProductInfo(slug) }
    }

    private suspend fun getProductInfo(slug: String) = coroutineScope {
        _isLoading.value = true
        getProductUseCase(slug)
            .flowOn(Dispatchers.IO)
            .catch { error -> Log.d("myLogs", error.message.toString()) }
            .collect {
                _productInfo.value = it
                _isLoading.value = false
            }
    }
}