package com.example.journalapp.feature_note.presentation.add_edit_note

data class NoteTextState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)