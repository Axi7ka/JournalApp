package com.example.journalapp.feature_note.presentation.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatDate(date: Long): String {
    val dateTime = LocalDateTime.ofEpochSecond(date / 1000, 0, java.time.ZoneOffset.UTC)
    val formatter = DateTimeFormatter.ofPattern("MMMM dd yyyy HH:mm", Locale.getDefault())
    return dateTime.format(formatter)
}