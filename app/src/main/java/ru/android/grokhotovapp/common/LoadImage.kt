package ru.android.grokhotovapp.common

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

sealed class ImageViewState {
    data class Success(val bitmap: Bitmap) : ImageViewState()
    object Loading : ImageViewState()
    data class Error(val error: ImageErrorStatus) : ImageViewState()
}

sealed class ImageErrorStatus {
    object NoImageProvided : ImageErrorStatus()
    object NoImageLoaded : ImageErrorStatus()
    data class RemoteError(val cause: Exception?) : ImageErrorStatus()
}

@Composable
fun LoadImage(url: String?): MutableState<ImageViewState> {
    val imageViewState: MutableState<ImageViewState> =
        remember(url) { mutableStateOf(ImageViewState.Loading) }

    if (url.isNullOrBlank()) {
        imageViewState.value = ImageViewState.Error(ImageErrorStatus.NoImageProvided)
    } else {
        Picasso.get().load(url).into(object : Target{
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                val image = bitmap ?: run {
                    imageViewState.value = ImageViewState.Error(ImageErrorStatus.NoImageLoaded)
                    return@onBitmapLoaded
                }
                imageViewState.value = ImageViewState.Success(image)
            }

            override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) {
                imageViewState.value = ImageViewState.Error(ImageErrorStatus.RemoteError(e))
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

        })
    }

    return imageViewState
}