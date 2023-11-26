package com.example.journalapp.di

import android.app.Application
import androidx.room.Room
import com.example.journalapp.feature_note.data.data_source.NoteDatabase
import com.example.journalapp.feature_note.data.repository.NoteRepositoryImpl
import com.example.journalapp.feature_note.domain.repository.NoteRepository
import com.example.journalapp.feature_note.domain.use_case.AddNoteUseCase
import com.example.journalapp.feature_note.domain.use_case.DeleteNoteUseCase
import com.example.journalapp.feature_note.domain.use_case.GetNoteUseCase
import com.example.journalapp.feature_note.domain.use_case.GetNotesUseCase
import com.example.journalapp.feature_note.domain.use_case.NoteDetailsUseCases
import com.example.journalapp.feature_note.domain.use_case.NotesUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideJournalDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteDetailsUseCases(repository: NoteRepository): NoteDetailsUseCases{
        return NoteDetailsUseCases(
            deleteNote = DeleteNoteUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideNotesUseCases(repository: NoteRepository): NotesUseCases {
        return NotesUseCases(
            getNotes = GetNotesUseCase(repository),
            addNote = AddNoteUseCase(repository),
            deleteNote = DeleteNoteUseCase(repository),
            getNote = GetNoteUseCase(repository)
        )
    }

}