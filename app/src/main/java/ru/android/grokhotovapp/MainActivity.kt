package ru.android.grokhotovapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.android.grokhotovapp.navigation.NavigationTree
import ru.android.grokhotovapp.presentation.categories.Categories
import ru.android.grokhotovapp.presentation.categories.CategoriesViewModel
import ru.android.grokhotovapp.presentation.listproducts.ProductsScreen
import ru.android.grokhotovapp.presentation.listproducts.ProductsScreenViewModel
import ru.android.grokhotovapp.presentation.productinfo.ProductInfo
import ru.android.grokhotovapp.presentation.productinfo.ProductInfoViewModel
import ru.android.grokhotovapp.ui.theme.GrokhotovAppTheme

@SuppressLint("RestrictedApi")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GrokhotovAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = NavigationTree.Categ.name
                ) {
                    composable(route = NavigationTree.Categ.name) {
                        val categoriesViewModel = hiltViewModel<CategoriesViewModel>()
                        Categories(viewModel = categoriesViewModel, navController = navController)
                    }
                    composable("${NavigationTree.ListProducts.name}/{slug}") {
                        val productsScreenViewModel = hiltViewModel<ProductsScreenViewModel>()
                        ProductsScreen(
                            viewModel = productsScreenViewModel,
                            navController = navController
                        )
                    }
                    composable(route = "${NavigationTree.ProductInfo.name}/{slug}") {
                        val productInfoViewModel = hiltViewModel<ProductInfoViewModel>()
                        ProductInfo(
                            viewModel = productInfoViewModel,
                            navController = navController,
                            onShareClick = { title, sku ->
                                shareProductInfo(title, sku)
                            })
                    }
                }
            }
        }
    }

    private fun shareProductInfo(title: String, sku: String) {
        val intent = Intent()
        intent.setAction(Intent.ACTION_SEND)
        intent.putExtra("title", title)
        intent.putExtra("sku", sku)
        intent.setType("text/plain")
        startActivity(intent)
    }
}