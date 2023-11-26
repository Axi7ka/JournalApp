package com.example.journalapp.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.journalapp.feature_note.domain.module.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase: RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object{
        const val DATABASE_NAME = "journal_db"
    }

}