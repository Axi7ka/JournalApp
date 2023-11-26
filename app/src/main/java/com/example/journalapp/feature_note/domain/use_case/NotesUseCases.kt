package com.example.journalapp.feature_note.domain.use_case

data class NotesUseCases(
    val getNotes: GetNotesUseCase,
    val addNote: AddNoteUseCase,
    val deleteNote: DeleteNoteUseCase,
    val getNote: GetNoteUseCase
)