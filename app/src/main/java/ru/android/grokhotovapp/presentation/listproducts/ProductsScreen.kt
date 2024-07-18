package ru.android.grokhotovapp.presentation.listproducts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.android.grokhotovapp.common.ProgressScreen
import ru.android.grokhotovapp.navigation.NavigationTree
import ru.android.grokhotovapp.presentation.categories.CategoriesViewModel
import ru.android.grokhotovapp.presentation.categories.CategoryAction
import ru.android.grokhotovapp.presentation.categories.CategoryEvent
import ru.android.grokhotovapp.ui.theme.AppTheme

@Composable
fun ProductsScreen(viewModel: CategoriesViewModel, navController: NavController) {
    val listProducts = viewModel.listProducts.collectAsState()
    val viewAction = viewModel.viewAction.collectAsState()
    val isLoading = viewModel.isListProductLoading.collectAsState()

    if (isLoading.value){
        ProgressScreen()
    } else {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(listProducts.value) { productInfo ->
                ItemListProducts(
                    productInfo = productInfo,
                    onItemClick = {
                        viewModel.obtaintEvent(
                            CategoryEvent.ProductInfoClicked(
                                productInfo
                            )
                        )
                    }
                )
            }
        }
    }

    LaunchedEffect(key1 = viewAction.value){
        when (viewAction.value) {
            CategoryAction.OpenProductInfo -> {
                navController.navigate(route = NavigationTree.ProductInfo.name){
                    popUpTo(route = NavigationTree.ListProducts.name)
                }
            }

            else -> {}
        }
    }


    DisposableEffect(key1 = Unit, effect = {
        onDispose { viewModel.obtaintEvent(CategoryEvent.CategoryActionInvoked) }
    })
}