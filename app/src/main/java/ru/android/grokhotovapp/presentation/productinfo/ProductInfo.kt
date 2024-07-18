package ru.android.grokhotovapp.presentation.productinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.android.grokhotovapp.R
import ru.android.grokhotovapp.common.PriceRow
import ru.android.grokhotovapp.domain.model.ProductInfo
import ru.android.grokhotovapp.presentation.categories.CategoriesViewModel
import ru.android.grokhotovapp.ui.theme.AppTheme

@Composable
fun ProductInfo(
    viewModel: CategoriesViewModel,
    navController: NavController,
    onShareClick: (String, String) -> Unit
) {
    val productInfo = viewModel.productInfo.collectAsState()
    val discount = productInfo.value.sizeDiscount > 0

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(
                onClick = { navController.popBackStack() },
            ) {
                Icon(
                    modifier = Modifier
                        .height(20.dp)
                        .width(21.dp),
                    painter = painterResource(id = R.drawable.ic_back_gray),
                    contentDescription = "",
                    tint = AppTheme.colors.primaryTintColor
                )
            }
            IconButton(
                onClick = {
                    onShareClick(
                        productInfo.value.title,
                        productInfo.value.sku.toString()
                    )
                },
            ) {
                Icon(
                    modifier = Modifier.size(26.dp),
                    painter = painterResource(id = R.drawable.ic_share_gray),
                    contentDescription = "",
                    tint = AppTheme.colors.primaryTintColor
                )
            }
        }
        HorizontalDivider(
            color = AppTheme.colors.dividerColor,
            thickness = 2.dp
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 39.3.dp), contentAlignment = Alignment.Center
        ) {
            productInfo.value.bitmap?.let {
                Image(
                    bitmap = it,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(208.7.dp)
                        .height(192.dp)
                )
            } //в противном случае "нет изображения"
        }
        Column(modifier = Modifier.padding(start = 19.3.dp, end = 12.6.dp)) {
            if (discount) {
                Box(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .background(
                            color = AppTheme.colors.discountColor,
                            shape = RoundedCornerShape(5.dp)
                        )
                ) {
                    Text(
                        text = "-${productInfo.value.sizeDiscount}%",
                        fontSize = 17.sp,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 6.1.dp, vertical = 5.7.dp)
                    )
                }
            }

            Text(
                modifier = Modifier.padding(top = 11.7.dp),
                text = "Арт. ${productInfo.value.sku}",
                color = AppTheme.colors.primaryTintColor,
                fontSize = 16.sp
            )
            Text(
                modifier = Modifier.padding(top = 5.3.dp),
                text = productInfo.value.title,
                color = AppTheme.colors.primaryTextColor,
                fontSize = 21.sp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 52.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                if (discount) {
                    PriceRow(
                        price = productInfo.value.price,
                        color = AppTheme.colors.discountColor
                    )
                } else {
                    PriceRow(
                        price = productInfo.value.price,
                        color = AppTheme.colors.primaryTextColor
                    )
                }

                if (discount) { //изменить под PriceRow
                    Row(
                        modifier = Modifier.padding(start = 24.2.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "${productInfo.value.priceOld} ₽",
                            style = TextStyle(
                                color = AppTheme.colors.headerTextColor,
                                fontSize = 22.sp,
                                textDecoration = TextDecoration.LineThrough
                            )
                        )
                        Text(
                            text = "/шт",
                            color = AppTheme.colors.headerTextColor,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}