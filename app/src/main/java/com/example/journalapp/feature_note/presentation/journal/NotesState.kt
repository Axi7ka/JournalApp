package com.example.journalapp.feature_note.presentation.journal

import com.example.journalapp.feature_note.domain.module.Note
import com.example.journalapp.feature_note.domain.util.NoteOrder
import com.example.journalapp.feature_note.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val note: Note = Note("", 0L, emptyList(), emptyList()),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)