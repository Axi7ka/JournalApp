package com.example.journalapp.feature_note.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.journalapp.feature_note.data.utils.Converter
import com.example.journalapp.feature_note.domain.module.Note

@TypeConverters(Converter::class)
@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "journal_db"

        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    DATABASE_NAME
                )
                    .addCallback(NoteDatabaseCallback(context)) // Pass NoteDao here
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}