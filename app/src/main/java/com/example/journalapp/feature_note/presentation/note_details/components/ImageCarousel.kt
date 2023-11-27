package com.example.journalapp.feature_note.presentation.note_details.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.journalapp.feature_note.domain.module.Note
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageCarousel(
    modifier: Modifier = Modifier,
    entry: Note
) {
    val pagerState = rememberPagerState(initialPage = 0)
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        entry.photo?.size?.let {
            HorizontalPager(
                pageCount = it,
                state = pagerState,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            ) { page ->
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(12.dp)) // Apply the corner radius here
                ) {
                    val painter = rememberImagePainter(
                        data = entry.photo[page],
                        builder = {
                            crossfade(true)
                            ImageRequest.Builder(LocalContext.current)
                                .data(entry.photo)
                                .build()
                        }
                    )

                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            entry.photo?.size?.let {
                repeat(it) { pageIndex ->
                    val selected = pageIndex == pagerState.currentPage
                    val color = if (selected) Color.Black else Color.Gray
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(color, MaterialTheme.shapes.small)
                            .clickable {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(pageIndex)
                                }
                            }
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}