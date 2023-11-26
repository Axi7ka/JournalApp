package com.example.journalapp.feature_note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.ui.focus.FocusState
import com.example.journalapp.feature_note.domain.module.Note

sealed class AddEditNoteEvent {
    data class EnteredText(val value: String): AddEditNoteEvent()
    data class ChangeTextFocus(val focusState: FocusState): AddEditNoteEvent()
    data class DeleteNote(val note: State<Note?>): AddEditNoteEvent()
    object SaveNote: AddEditNoteEvent()
}