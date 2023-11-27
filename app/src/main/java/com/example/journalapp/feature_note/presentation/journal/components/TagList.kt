package com.example.journalapp.feature_note.presentation.journal.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TagList(tags: List<String?>) {
    LazyRow(
        content = {
            itemsIndexed(tags) { index, tag ->
                tag?.let {
                    TagItem(it)
                    if (index != tags.lastIndex) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
    )
}