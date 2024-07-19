package ru.android.grokhotovapp.presentation.listproducts

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import ru.android.grokhotovapp.common.ProgressScreen
import ru.android.grokhotovapp.navigation.NavigationTree

@Composable
fun ProductsScreen(
    viewModel: ProductsScreenViewModel,
    navController: NavController
) {
    val listProducts = viewModel.listProducts.collectAsState()
    val viewAction = viewModel.viewAction.collectAsState()
    val isLoading = viewModel.isListProductLoading.collectAsState()

    if (isLoading.value) {
        ProgressScreen()
    } else {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(listProducts.value) { productInfo ->
                ItemListProducts(
                    productInfo = productInfo,
                    onItemClick = {
                        viewModel.obtainEvent(
                            ProductsScreenEvent.ProductInfoClicked(productInfo.productSlug)
                        )
                    }
                )
            }
        }
    }

    LaunchedEffect(key1 = viewAction.value) {
        when (val action = viewAction.value) {
            is ProductsScreenAction.OpenProductInfo -> {
                navController.navigate(route = "${NavigationTree.ProductInfo.name}/${action.slug}") {
                    popUpTo(route = NavigationTree.ListProducts.name)
                }
            }

            else -> {}
        }
    }


    DisposableEffect(key1 = Unit, effect = {
        onDispose { viewModel.obtainEvent(ProductsScreenEvent.ProductsScreenActionInvoked) }
    })
}