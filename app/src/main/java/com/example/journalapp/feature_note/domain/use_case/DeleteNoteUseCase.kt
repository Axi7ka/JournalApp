package com.example.journalapp.feature_note.domain.use_case

import com.example.journalapp.feature_note.domain.module.Note
import com.example.journalapp.feature_note.domain.repository.NoteRepository

class DeleteNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note){
        repository.deleteNote(note)
    }
}