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
import ru.android.grokhotovapp.presentation.productinfo.ProductInfo
import ru.android.grokhotovapp.ui.theme.GrokhotovAppTheme

@SuppressLint("RestrictedApi")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GrokhotovAppTheme {
                val navController = rememberNavController()
                navController.addOnDestinationChangedListener { controller, destination, arguments ->
                    val routes = controller
                        .backQueue
                        .map { it.destination.route }
                        .joinToString(", ")

                    Log.d("BackStackLog", "BackStack: $routes")
                }

                val categoriesViewModel = hiltViewModel<CategoriesViewModel>()
                NavHost(navController = navController, startDestination = NavigationTree.Categ.name) {
                    composable(route = NavigationTree.Categ.name) {
                        Categories(viewModel = categoriesViewModel, navController = navController)
                    }
                    composable(route = NavigationTree.ListProducts.name) {
                        Log.d("myLogs", "MainActivity.kt ProductsSceen")
                        ProductsScreen(
                            viewModel = categoriesViewModel,
                            navController = navController
                        )
                    }
                    composable(route = NavigationTree.ProductInfo.name) {
                        ProductInfo(
                            viewModel = categoriesViewModel,
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