package com.example.journalapp.feature_note.presentation.journal.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.journalapp.feature_note.domain.module.Note
import com.example.journalapp.feature_note.presentation.util.formatDate


@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = formatDate(note.date),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            if (!note.photo.isNullOrEmpty() && note.photo.any { it.isNotBlank() }) {
                Spacer(modifier = Modifier.height(8.dp))
                ImageItem(
                    imageUrl = note.photo.first(),
                    entry = note,
                    contentScale = ContentScale.Crop,
                    height = 100.dp
                )
            }
            if (!note.tags.isNullOrEmpty() && note.tags.any { it.isNotBlank() }) {
                note.tags.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    TagList(tags = it)
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }

}

