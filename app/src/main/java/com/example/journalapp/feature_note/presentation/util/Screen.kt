package com.example.journalapp.feature_note.presentation.util

sealed class Screen(val route: String) {
    object JournalScreen: Screen("notes_screen")
    object AddNoteScreen: Screen("add_edit_note_screen")
    object NoteDetailsScreen: Screen("note_details_screen")
}
