package com.example.journalapp.feature_note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
class AddNoteViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases,
) : ViewModel() {

    private val _noteText = mutableStateOf(
        NoteTextState(
            hint = "Enter Text..."
        )
    )
    val noteText: State<NoteTextState> = _noteText

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    fun onEvent(event: AddNoteEvent) {
        when (event) {
            is AddNoteEvent.EnteredText -> {
                _noteText.value = noteText.value.copy(
                    text = event.value
                )
            }

            is AddNoteEvent.ChangeTextFocus -> {
                _noteText.value = noteText.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteText.value.text.isBlank()
                )
            }

            is AddNoteEvent.SaveNote -> {
                saveNote()
            }

            else -> {}
        }
    }

    private fun saveNote() {
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
                    UiEvent.ShowSnackBar(
                        message = e.message ?: "Couldn't save note"
                    )
                )
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }
}