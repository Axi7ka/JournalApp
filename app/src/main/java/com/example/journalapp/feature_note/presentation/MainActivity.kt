package com.example.journalapp.feature_note.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.journalapp.feature_note.presentation.add_edit_note.AddNoteScreen
import com.example.journalapp.feature_note.presentation.journal.JournalScreen
import com.example.journalapp.feature_note.presentation.note_details.NoteDetailsScreen
import com.example.journalapp.feature_note.presentation.note_details.NoteDetailsViewModel
import com.example.journalapp.feature_note.presentation.ui.theme.JournalAppTheme
import com.example.journalapp.feature_note.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

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
                        startDestination = Screen.JournalScreen.route
                    ) {
                        composable(route = Screen.JournalScreen.route) {
                            JournalScreen(
                                navController = navController,
                            )
                        }
                        composable(
                            route = Screen.AddNoteScreen.route,
                        ) {
                            AddNoteScreen(
                                navController = navController,
                            )
                        }
                        composable(
                            route = Screen.NoteDetailsScreen.route +
                                    "?noteId={noteId}",
                            arguments = listOf(
                                navArgument(
                                    name = "noteId"
                                ) {
                                    type = NavType.IntType
                                }
                            )
                        ) {
                            val viewModel = hiltViewModel<NoteDetailsViewModel>()
                            val state by viewModel.state.collectAsState()
                            NoteDetailsScreen(
                                state = state,
                                navController = navController,
                            )

                            val noteId = it.arguments?.getInt("noteId") ?: -1
                            Log.d("Navigation", "Navigated to NoteDetailsScreen with noteId: $noteId")
                        }
                    }
                }
            }
        }
    }
}