package com.example.journalapp.feature_note.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.journalapp.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.example.journalapp.feature_note.presentation.add_edit_note.AddEditNoteViewModel
import com.example.journalapp.feature_note.presentation.journal.JournalViewModel
import com.example.journalapp.feature_note.presentation.journal.NotesScreen
import com.example.journalapp.feature_note.presentation.ui.theme.JournalAppTheme
import com.example.journalapp.feature_note.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val journalViewModel: JournalViewModel by viewModels()
    private val addEditNoteViewModel: AddEditNoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JournalAppTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotesScreen.route
                    ) {
                        composable(route = Screen.NotesScreen.route) {
                            NotesScreen(
                                navController = navController,
                                viewModel = journalViewModel
                            )
                        }
                        composable(
                            route = Screen.AddEditNoteScreen.route +
                                    "?noteId={noteId}",
                            arguments = listOf(
                                navArgument(
                                    name = "noteId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            val noteId = it.arguments?.getInt("noteId") ?: -1
                            Log.d("Navigation", "Navigated to AddEditNoteScreen with noteId: $noteId")

                            AddEditNoteScreen(
                                navController = navController,
                                viewModel = addEditNoteViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}