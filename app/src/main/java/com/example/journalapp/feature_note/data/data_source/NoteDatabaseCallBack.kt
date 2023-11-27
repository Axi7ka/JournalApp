package com.example.journalapp.feature_note.data.data_source

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.journalapp.R
import com.example.journalapp.feature_note.domain.module.Note

class NoteDatabaseCallback(private val context: Context) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        val firstImgDrawable = R.drawable.img_dogs
        val secondImgDrawable = R.drawable.img_2
        val thirdImgDrawable = R.drawable.img_3
        val forthImgDrawable = R.drawable.img_run

        val photoUri1 = "android.resource://${context.packageName}/$firstImgDrawable"
        val photoUri2 = "android.resource://${context.packageName}/$secondImgDrawable"
        val photoUri3 = "android.resource://${context.packageName}/$thirdImgDrawable"
        val photoUri4 = "android.resource://${context.packageName}/$forthImgDrawable"

        val defaultNotes = listOf(
            Note(
                title = "My inner motivation to get better every day.",
                date = 1701105333000,
                photo = listOf(photoUri1, photoUri2, photoUri3),
                tags = listOf("health", "life", "motivation")
            ),
            Note(
                title = "Today I went running with a group of friends and my dogs.",
                date = 1700414133000,
                photo = listOf(photoUri4, photoUri1, photoUri3,photoUri2),
                tags = listOf("workout", "motivation")
            ),
            Note(
                title = "Today was even better than yesterday. The morning sun greeted me with its warmth, inviting me to step out for a refreshing walk. I decided to take a leisurely stroll with my furry companions. The journey energized us for the day ahead.",
                date = 1699345020000,
                photo = null,
                tags = listOf("sunny", "life")
            ),
            Note(
                title = "The day got even better as I've adopted a new friend.",
                date = 1696688400000,
                photo = listOf(photoUri3),
                tags = listOf("newFriend", "happiness")
            ),
            Note(
                title = "This is going to be a good day",
                date = 1696666111000,
                photo = null,
                tags = null
            )

        )

        defaultNotes.forEach { note ->
            val contentValues = ContentValues()
            contentValues.put("title", note.title)
            contentValues.put("date", note.date)
            val photosAsString = note.photo?.joinToString(separator = ",") ?: ""
            contentValues.put("photo", photosAsString)
            val tagsAsString = note.tags?.joinToString(separator = ",") ?: ""
            contentValues.put("tags", tagsAsString)
            db.insert("note", SQLiteDatabase.CONFLICT_REPLACE, contentValues)
        }
    }
}
