package com.example.journalapp.feature_note.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent {
    data class EnteredText(val value: String): AddEditNoteEvent()
    data class ChangeTextFocus(val focusState: FocusState): AddEditNoteEvent()
    object DeleteNote: AddEditNoteEvent()
    object SaveNote: AddEditNoteEvent()
}