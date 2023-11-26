package com.example.journalapp.feature_note.data.data_source

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.journalapp.R
import com.example.journalapp.feature_note.domain.module.Note

class NoteDatabaseCallback(private val context: Context) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        Log.d("NoteDatabaseCallback", "onCreate called")

        val drawableResId = R.drawable.img_dogs
        val photoUri = "android.resource://${context.packageName}/$drawableResId"

        val defaultNotes = listOf(
            Note("Default Note 1", System.currentTimeMillis(), photoUri, listOf("tag1","tag2")),
            Note("Default Note 2", System.currentTimeMillis(), null, listOf("tag1","tag2")),
        )


        defaultNotes.forEach { note ->
            val contentValues = ContentValues()
            contentValues.put("title", note.title)
            contentValues.put("date", note.date)
            contentValues.put("photo", note.photo)
            val tagsAsString = note.tags?.joinToString(separator = ",") ?: ""
            contentValues.put("tags", tagsAsString)
            db.insert("note", SQLiteDatabase.CONFLICT_REPLACE, contentValues)
        }
    }
}
