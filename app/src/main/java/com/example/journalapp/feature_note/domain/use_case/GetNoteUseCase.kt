package com.example.journalapp.feature_note.domain.use_case

import android.util.Log
import com.example.journalapp.feature_note.domain.module.Note
import com.example.journalapp.feature_note.domain.repository.NoteRepository

class GetNoteUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Int): Note? {
        Log.d("GetNoteUseCase", "Requested note ID: $id")
        return repository.getNoteById(id)
    }

}