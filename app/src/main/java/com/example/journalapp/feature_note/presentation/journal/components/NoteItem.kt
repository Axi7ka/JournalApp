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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.journalapp.feature_note.domain.module.Note
import com.example.journalapp.feature_note.presentation.util.formatDate


@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier
) {

    val formattedDate = remember {
        formatDate(note.date)
    }

    Box(
        modifier = modifier.background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = formattedDate,
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
            Spacer(modifier = Modifier.height(8.dp))
            note.photo?.let {
                ImageItem(
                    imageUrl = note.photo,
                    entry = note
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            note.tags?.let { TagList(tags = it) }
        }
    }

}

