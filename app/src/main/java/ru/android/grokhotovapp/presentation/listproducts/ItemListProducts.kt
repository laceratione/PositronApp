package ru.android.grokhotovapp.presentation.listproducts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import ru.android.grokhotovapp.R
import ru.android.grokhotovapp.di.NetworkModule
import ru.android.grokhotovapp.domain.model.ProductInfo
import ru.android.grokhotovapp.ui.theme.AppTheme

@Composable
fun ItemListProducts(productInfo: ProductInfo, onItemClick: () -> Unit) {
    val isHaveDiscount = productInfo.sizeDiscount > 0
    val imageURL = NetworkModule.baseIconUrl + productInfo.images[0]//fix

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = onItemClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 26.7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .padding(start = 20.dp),
                contentAlignment = Alignment.TopStart
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxSize(),
                    model = imageURL,
                    loading = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = AppTheme.colors.colorPrimary,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    },
                    contentDescription = ""
                )

                if (isHaveDiscount) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = AppTheme.colors.discountColor,
                                shape = RoundedCornerShape(5.dp)
                            )
                    ) {
                        Text(
                            text = "-${productInfo.sizeDiscount}%",
                            fontSize = 15.sp,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 5.5.dp, vertical = 4.2.dp)
                        )
                    }
                }
            }
            Column(modifier = Modifier.padding(start = 40.7.dp, end = 52.dp)) {
                Text(
                    text = "Артикул ${productInfo.sku}",
                    color = AppTheme.colors.primaryTintColor,
                    fontSize = 13.sp
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = productInfo.title,
                    color = AppTheme.colors.primaryTextColor,
                    fontSize = 15.sp
                )
                Row(
                    modifier = Modifier.padding(top = 10.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "${productInfo.price} ₽",
                        color = AppTheme.colors.primaryTextColor,
                        fontSize = 19.sp
                    )
                    if (isHaveDiscount) {
                        Text(
                            modifier = Modifier.padding(start = 15.3.dp),
                            text = "${productInfo.priceOld} ₽",
                            style = TextStyle(
                                color = AppTheme.colors.headerTextColor,
                                fontSize = 13.sp,
                                textDecoration = TextDecoration.LineThrough
                            )
                        )
                    }
                }

                //настроить шрифт текста всего приложения
                if (productInfo.countAvailable > 0) {
                    Text(
                        modifier = Modifier.padding(top = 5.3.dp),
                        text = stringResource(id = R.string.isStock),
                        color = AppTheme.colors.colorPrimary,
                        fontSize = 15.sp
                    )
                }
            }
        }
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .padding(start = 19.3.dp, top = 32.7.dp)
        ) {
            Icon(
                modifier = Modifier
                    .height(23.3.dp)
                    .width(24.7.dp),
                painter = painterResource(id = R.drawable.ic_favorite),
                contentDescription = "",
                tint = AppTheme.colors.primaryTintColor
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(top = 30.7.dp),
            color = AppTheme.colors.dividerColor,
            thickness = 2.dp
        )
    }
}