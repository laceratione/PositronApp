package ru.android.grokhotovapp.presentation.categories

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import ru.android.grokhotovapp.R
import ru.android.grokhotovapp.common.ProgressScreen
import ru.android.grokhotovapp.di.NetworkModule
import ru.android.grokhotovapp.navigation.NavigationTree
import ru.android.grokhotovapp.ui.theme.AppTheme

@Composable
fun Categories(
    viewModel: CategoriesViewModel,
    navController: NavController
) {
    val currentCateg = viewModel.currentCateg.collectAsState()
    val viewAction = viewModel.viewAction.collectAsState()
    val isLoading = viewModel.isCatalogLoading.collectAsState()

    if (isLoading.value) {
        ProgressScreen()
    } else {
        Column {
            if (currentCateg.value.isRoot) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AppTheme.colors.colorPrimary)
                        .padding(bottom = 22.dp, top = 22.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.header_categories_screen),
                        modifier = Modifier.padding(start = 18.7.dp),
                        fontSize = 24.sp,
                        color = AppTheme.colors.textColorPrimary
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AppTheme.colors.colorPrimary)
                        .padding(bottom = 22.dp, top = 22.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .height(20.dp)
                                .width(21.dp)
                                .clickable {
                                    viewModel.obtaintEvent(CategoryEvent.BackClicked)
                                },
                            painter = painterResource(id = R.drawable.ic_back_gray),
                            contentDescription = "",
                            tint = Color.White
                        )
                        Text(//расположить в центре
                            text = currentCateg.value.title,
                            modifier = Modifier.padding(start = 61.3.dp),
                            fontSize = 24.sp,
                            color = AppTheme.colors.textColorPrimary
                        )
                    }
                }
            }


            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(currentCateg.value.subCategories) { category ->
                    val iconURL = NetworkModule.baseIconUrl + category.icon

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.obtaintEvent(CategoryEvent.CategoryClicked(category))
                                Log.d("myLogs", "Categories.kt click: ${category.slug}")
                            }
                            .padding(
                                start = 19.3.dp,
                                top = 18.9.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = iconURL,
                            contentDescription = null,
                            modifier = Modifier.size(28.dp)
                        )

                        Text(
                            text = category.title,
                            fontSize = 17.sp,
                            color = AppTheme.colors.headerTextColor,
                            modifier = Modifier.padding(start = 15.dp)
                        )
                    }

                }
            }
        }
    }


    LaunchedEffect(key1 = viewAction.value) {
        when (val action = viewAction.value) {
            is CategoryAction.OpenListProducts -> {
                navController.navigate(route = "${NavigationTree.ListProducts.name}/${action.slug}") {
                    popUpTo(route = NavigationTree.Categ.name)
                }
                viewModel.obtaintEvent(CategoryEvent.CategoryActionInvoked)
            }

            else -> {}
        }
    }
}