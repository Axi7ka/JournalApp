package com.example.journalapp.feature_note.presentation.journal

import com.example.journalapp.feature_note.domain.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    object ToggleOrderSection: NotesEvent()
}