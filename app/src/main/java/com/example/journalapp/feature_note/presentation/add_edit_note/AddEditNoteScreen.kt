package com.example.journalapp.feature_note.presentation.add_edit_note

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.journalapp.feature_note.presentation.add_edit_note.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    navController: NavController,
    viewModel: AddEditNoteViewModel
) {
    val textState = viewModel.noteText.value
    val scaffoldState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddEditNoteViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditNoteViewModel.UiEvent.SaveNote -> {
                    navController.popBackStack()
                }
            }
        }
    }

    // Log the initial textState when the composable recomposes
    Log.d("AddEditNoteScreen", "Initial textState: ${textState.text}")

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditNoteEvent.SaveNote)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Save note"
                )
            }
        },
        snackbarHost = { SnackbarHost(scaffoldState) },
    ) {
        TransparentHintTextField(
            text = textState.text,
            hint = textState.hint,
            onValueChange = {
                viewModel.onEvent(AddEditNoteEvent.EnteredText(it))

                // Log the text when it changes
                Log.d("AddEditNoteScreen", "Text changed: $it")
            },
            onFocusChange = {
                viewModel.onEvent(AddEditNoteEvent.ChangeTextFocus(it))

                // Log the focus change
                Log.d("AddEditNoteScreen", "Focus changed: $it")
            },
            isHintVisible = textState.isHintVisible,
            textStyle = MaterialTheme.typography.bodyMedium
        )
    }
}