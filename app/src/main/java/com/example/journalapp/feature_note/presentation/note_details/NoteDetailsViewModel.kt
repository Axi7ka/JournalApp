package com.example.journalapp.feature_note.presentation.note_details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.journalapp.feature_note.domain.module.Note
import com.example.journalapp.feature_note.domain.use_case.NotesUseCases
import com.example.journalapp.feature_note.presentation.add_edit_note.AddNoteEvent
import com.example.journalapp.feature_note.presentation.util.DefaultStateDelegate
import com.example.journalapp.feature_note.presentation.util.StateDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailsViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), StateDelegate<NoteDetailsViewModel.State> by DefaultStateDelegate(State.Loading) {


    private var currentNoteId: Int? = null
    private val id: Int = savedStateHandle.get<Int>("noteId") ?: 1

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            subscribeToDataChanges()
        }
    }

    private suspend fun subscribeToDataChanges() {
        viewModelScope.launch {
            notesUseCases.getNote(id)?.also { note ->
                currentNoteId = note.id
                currentState = State.Value(note)
                Log.d("NoteDetailsViewModel", "Received note: $note")
            }
        }
    }

    fun onEvent(event: AddNoteEvent) {
        when (event) {
            is AddNoteEvent.DeleteNote -> {
                deleteNote()
            }

            else -> {}
        }
    }

    private fun deleteNote() {
        val noteToDelete = (currentState as? State.Value)?.note // Retrieve the current note
        viewModelScope.launch {
            try {
                noteToDelete?.let {
                    notesUseCases.deleteNote(it)
                    _eventFlow.emit(UiEvent.NavigateBack)
                    Log.d("NoteDetailsViewModel", "Deleting note: $noteToDelete")
                }
            } catch (e: Exception) {
                _eventFlow.emit(
                    UiEvent.ShowSnackBar(
                        message = "Error deleting note: ${e.message}"
                    )
                )
            }
        }
    }

    sealed class State {
        object Loading : State()
        data class Value(val note: Note) : State()
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object NavigateBack : UiEvent()
    }
}