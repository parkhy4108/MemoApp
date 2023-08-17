package com.dev_young.note.domain.repository

import com.dev_young.note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getNotes() : Flow<List<Note>>
    suspend fun getNote(id: Int) : Note
    suspend fun upsertNote(note: Note)
    suspend fun deleteNote(note: Note)
}