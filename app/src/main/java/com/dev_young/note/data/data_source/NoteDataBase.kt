package com.dev_young.note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dev_young.note.domain.model.Note


@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDataBase : RoomDatabase() {
    companion object{
        const val DB_NAME = "noteDB"
    }
    abstract fun dao() : Dao
}