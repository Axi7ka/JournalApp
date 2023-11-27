package com.example.journalapp.feature_note.presentation.note_details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.journalapp.feature_note.presentation.add_edit_note.AddNoteEvent
import com.example.journalapp.feature_note.presentation.journal.components.ImageItem
import com.example.journalapp.feature_note.presentation.journal.components.TagList
import com.example.journalapp.feature_note.presentation.note_details.components.CenteredCircularProgressIndicator
import com.example.journalapp.feature_note.presentation.note_details.components.ImageCarousel
import com.example.journalapp.feature_note.presentation.util.formatDate
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailsScreen(
    state: NoteDetailsViewModel.State,
    navController: NavController,
    viewModel: NoteDetailsViewModel = hiltViewModel()
) {
    val scaffoldState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is NoteDetailsViewModel.UiEvent.ShowSnackBar -> {
                    scaffoldState.showSnackbar(
                        message = event.message
                    )
                }

                is NoteDetailsViewModel.UiEvent.NavigateBack -> {
                    navController.popBackStack()
                }
            }
        }
    }

    if (state is NoteDetailsViewModel.State.Value) {
        Scaffold(
            snackbarHost = { SnackbarHost(scaffoldState) },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                        Text(
                            text = "Back",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                //TODO
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit"
                            )
                        }
                        IconButton(
                            onClick = {
                                viewModel.onEvent(AddNoteEvent.DeleteNote(state.note))
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete note"
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = state.note.date.let { formatDate(it) },
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                )
                Spacer(modifier = Modifier.height(8.dp))
                state.note.title.let { title ->
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.Black,
                    )
                }
                if (!state.note.photo.isNullOrEmpty() && state.note.photo.any { it.isNotBlank() }) {
                    if (state.note.photo.size == 1) {
                        Spacer(modifier = Modifier.height(16.dp))
                        ImageItem(
                            imageUrl = state.note.photo.first(),
                            entry = state.note,
                            contentScale = ContentScale.FillWidth,
                            height = 200.dp
                        )
                    } else if (state.note.photo.size > 1) {
                        Spacer(modifier = Modifier.height(16.dp))
                        ImageCarousel(
                            entry = state.note
                        )
                    }
                }
                if (!state.note.tags.isNullOrEmpty() && state.note.tags.any { it.isNotBlank() }) {
                    Spacer(modifier = Modifier.height(16.dp))
                    state.note.tags.let { TagList(tags = it) }
                }
            }
        }
    } else {
        CenteredCircularProgressIndicator()
    }
}
