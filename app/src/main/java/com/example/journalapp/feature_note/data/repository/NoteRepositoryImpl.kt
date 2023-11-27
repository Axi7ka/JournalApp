package com.example.journalapp.feature_note.data.repository

import android.util.Log
import com.example.journalapp.feature_note.data.data_source.NoteDao
import com.example.journalapp.feature_note.domain.module.Note
import com.example.journalapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes().onEach { notes ->
            Log.d("NoteRepositoryImpl", "Received ${notes.size} notes from the database")
            notes.forEachIndexed { index, note ->
                Log.d(
                    "NoteRepositoryImpl",
                    "Note $index: ${note.title}, ${note.date}, ${note.photo}, ${note.tags}"
                )
            }
        }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return kotlinx.coroutines.withContext(Dispatchers.IO) {
            delay(1000)

            val note = dao.getNoteById(id)
            if (note != null) {
                Log.d(
                    "NoteRepositoryImpl",
                    "Retrieved note by ID: ${note.title}, ${note.date}, ${note.photo}, ${note.tags}"
                )
            } else {
                Log.d("NoteRepositoryImpl", "Note with ID $id not found")
            }
            note
        }
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
        Log.d(
            "NoteRepositoryImpl",
            "Inserted note: ${note.title}, ${note.date}, ${note.photo}, ${note.tags}"
        )
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
        Log.d(
            "NoteRepositoryImpl",
            "Deleted note: ${note.title}, ${note.date}, ${note.photo}, ${note.tags}"
        )
    }
}