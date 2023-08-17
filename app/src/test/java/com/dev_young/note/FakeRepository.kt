package com.dev_young.note

import com.dev_young.note.domain.model.Note
import com.dev_young.note.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepository : Repository {
    private val notes = mutableListOf<Note>()

    override fun getNotes(): Flow<List<Note>> {
        return flow { emit(notes) }
    }

    override suspend fun getNote(id: Int): Note {
        return notes.find { it.id == id }!!
    }

    override suspend fun upsertNote(note: Note) {
        notes.add(note)
    }

    override suspend fun deleteNote(note: Note) {
        notes.remove(note)
    }

}