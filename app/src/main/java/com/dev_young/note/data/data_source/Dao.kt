package com.dev_young.note.data.data_source

import androidx.room.*
import androidx.room.Dao
import com.dev_young.note.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id= :id")
    suspend fun getNote(id: Int) : Note

    @Upsert
    suspend fun upsertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

}