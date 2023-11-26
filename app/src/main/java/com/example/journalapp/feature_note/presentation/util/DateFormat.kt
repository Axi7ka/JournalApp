package com.example.journalapp.feature_note.presentation.util

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(date: Long): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    return dateFormat.format(date)
}