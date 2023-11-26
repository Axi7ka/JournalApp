package com.example.journalapp.feature_note.presentation.journal.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.journalapp.feature_note.domain.module.Note

@Composable
fun ImageItem(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    entry: Note
) {
    Box(
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth()
    ) {
        val painter = rememberImagePainter(
            data = imageUrl,
            builder = {
                crossfade(true)
                ImageRequest.Builder(LocalContext.current)
                    .data(entry.photo)
                    .build()
                Log.d("ImageLoad", "Loading image with URL: $imageUrl")
            }
        )

        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}