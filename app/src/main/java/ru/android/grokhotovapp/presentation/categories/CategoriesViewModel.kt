package ru.android.grokhotovapp.presentation.categories

import android.util.Log
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
import ru.android.grokhotovapp.domain.model.Category
import ru.android.grokhotovapp.domain.model.ProductInfo
import ru.android.grokhotovapp.domain.usecase.GetCategories
import ru.android.grokhotovapp.domain.usecase.GetProduct
import ru.android.grokhotovapp.domain.usecase.GetSlugsProducts
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    val getCategoriesUseCase: GetCategories,
    val getSlugsProductsUseCase: GetSlugsProducts,
    val getProductUseCase: GetProduct
) : ViewModel() {
    private val _categories: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private var _currentCateg = MutableStateFlow(Category("", "", emptyList(), ""))
    val currentCateg: StateFlow<Category> = _currentCateg.asStateFlow()

    private val _listProducts: MutableStateFlow<List<ProductInfo>> = MutableStateFlow(emptyList())
    val listProducts: StateFlow<List<ProductInfo>> = _listProducts.asStateFlow()

    private val _productInfo: MutableStateFlow<ProductInfo> =
        MutableStateFlow(ProductInfo("", 0, "", images = emptyList()))
    val productInfo: StateFlow<ProductInfo> = _productInfo.asStateFlow()

    private val _isListProductLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isListProductLoading: StateFlow<Boolean> = _isListProductLoading.asStateFlow()

    private val _isCatalogLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isCatalogLoading: StateFlow<Boolean> = _isCatalogLoading.asStateFlow()

    private val _viewAction: MutableStateFlow<CategoryAction> =
        MutableStateFlow(CategoryAction.None)
    val viewAction: StateFlow<CategoryAction> = _viewAction.asStateFlow()

    private val _stackCategories: MutableList<Category> = mutableListOf()

    init {
        viewModelScope.launch {
            _isCatalogLoading.value = true
            getCategoriesUseCase()
                .flowOn(Dispatchers.IO)
                .catch { error -> Log.d("myLogs", error.message.toString()) }
                .collect {
                    _categories.value = it
                    _currentCateg.value =
                        Category("", "", subCategories = _categories.value, "", isRoot = true)
                    _isCatalogLoading.value = false
                }
        }

    }

    fun obtaintEvent(event: CategoryEvent) {
        when (event) {
            is CategoryEvent.CategoryClicked -> categoryAction(event.category)
            CategoryEvent.CategoryActionInvoked -> categoryActionInvoked()
            CategoryEvent.ListProductsClicked -> listProductsAction()
            is CategoryEvent.ProductInfoClicked -> productInfoAction(event.info)
            CategoryEvent.BackClicked -> backAction()
        }
    }

    private fun backAction() {
        _currentCateg.value = _stackCategories.last()
        _stackCategories.removeLast()
    }

    private fun productInfoAction(info: ProductInfo) {
        _productInfo.value = info
        _viewAction.value = CategoryAction.OpenProductInfo
    }

    private fun listProductsAction() {
        _viewAction.value = CategoryAction.OpenListProducts
    }

    private fun categoryActionInvoked() {
        _viewAction.value = CategoryAction.None
    }

    private fun categoryAction(category: Category) {
        if (category.subCategories.isNotEmpty()) {
            _stackCategories.add(_currentCateg.value)
            _currentCateg.value = category
            _viewAction.value = CategoryAction.OpenSubCategory
        } else {
            viewModelScope.launch { getListProducts(category.slug) }
            listProductsAction()
        }
    }

    //добавить отмену корутин
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