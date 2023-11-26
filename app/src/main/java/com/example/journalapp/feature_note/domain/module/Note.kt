package com.example.journalapp.feature_note.domain.module

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val title: String,
    val date: Long,
    val photo: String?,
    val tags: String?,
    @PrimaryKey
    val id: Int? = null
)

class InvalidNoteException(message: String): Exception(message)
