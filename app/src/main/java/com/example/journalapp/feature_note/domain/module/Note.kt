package com.example.journalapp.feature_note.domain.module

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.journalapp.feature_note.presentation.ui.theme.Pink80
import com.example.journalapp.feature_note.presentation.ui.theme.Purple80
import com.example.journalapp.feature_note.presentation.ui.theme.PurpleGrey80

@Entity
data class Note(
    val title: String,
    val date: Long,
    val photo: String?,
    val tags: String?,
    val color: Int,
    @PrimaryKey
    val id: Int? = null
){
    companion object{
        val notColors = listOf(Purple80, PurpleGrey80, Pink80)
    }
}
