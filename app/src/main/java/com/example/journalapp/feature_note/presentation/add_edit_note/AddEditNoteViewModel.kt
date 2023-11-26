package com.example.journalapp.feature_note.presentation.add_edit_note

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.journalapp.feature_note.domain.module.InvalidNoteException
import com.example.journalapp.feature_note.domain.module.Note
import com.example.journalapp.feature_note.domain.use_case.NotesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

//    private val _state = mutableStateOf(NotesState())
//    val state: State<NotesState> = _state

    private val _noteDetails = mutableStateOf<Note?>(null)
    val noteDetails: State<Note?> = _noteDetails


    private val _noteText = mutableStateOf(
        NoteTextState(
            hint = "Enter Text..."
        )
    )
    val noteText: State<NoteTextState> = _noteText

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->

                viewModelScope.launch {
                    notesUseCases.getNote(noteId)?.also { note ->
                        Log.d("NoteDetails", "Received note: $note")
                        currentNoteId = note.id
                        Log.d("currentnoteid", "Received note: $note")
                        _noteText.value = noteText.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteDetails.value = noteDetails.value?.copy(
                            title = note.title,
                            photo = note.photo,
                            tags = note.tags,
                            id = note.id,
                            date = note.date
                        )
                    }

            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredText -> {
                _noteText.value = noteText.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.ChangeTextFocus -> {
                _noteText.value = noteText.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteText.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        notesUseCases.addNote(
                            Note(
                                title = noteText.value.text,
                                date = System.currentTimeMillis(),
                                photo = null,
                                tags = null,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save note"
                            )
                        )
                    }
                }
            }

            is AddEditNoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    try {
                        event.note?.let {
                            noteDetails.value?.let { it1 -> notesUseCases.deleteNote(it1) }
                            _eventFlow.emit(UiEvent.NavigateBack)
                            Log.d("AddEditNoteViewModel", "Deleting note: ${event.note}")
                        }
                    } catch (e: Exception) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = "Error deleting note: ${e.message}"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
        object NavigateBack : UiEvent()
    }
}