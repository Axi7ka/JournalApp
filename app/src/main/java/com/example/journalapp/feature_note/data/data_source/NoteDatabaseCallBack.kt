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

        val photoUri1 = "android.resource://${context.packageName}/$firstImgDrawable"
        val photoUri2 = "android.resource://${context.packageName}/$secondImgDrawable"
        val photoUri3 = "android.resource://${context.packageName}/$thirdImgDrawable"


        val defaultNotes = listOf(
            Note(
                title = "My inner motivation to get better every day.",
                date = 1700424873000,
                photo = listOf(photoUri1, photoUri2, photoUri3),
                tags = listOf("health", "life", "motivation")
            ),
            Note(
                title = "Today was even better than yesterday. The morning sun greeted me with its warmth, inviting me to step out for a refreshing walk. I decided to take a leisurely stroll with my furry companions, my beloved dogs. As we embarked on our journey, the crisp air filled our lungs, energizing us for the day ahead.",
                date = 1699345020000,
                photo = null,
                tags = listOf("sunny", "life")
            ),
            Note(
                title = "This was actually a good day",
                date = 1696688400000,
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
