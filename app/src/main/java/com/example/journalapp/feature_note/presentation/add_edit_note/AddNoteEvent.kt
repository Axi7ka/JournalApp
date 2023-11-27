package com.example.journalapp.feature_note.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState
import com.example.journalapp.feature_note.domain.module.Note

sealed class AddNoteEvent {
    data class EnteredText(val value: String) : AddNoteEvent()
    data class ChangeTextFocus(val focusState: FocusState) : AddNoteEvent()
    data class DeleteNote(val note: Note?) : AddNoteEvent()
    object SaveNote : AddNoteEvent()
}