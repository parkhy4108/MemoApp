package com.dev_young.note.data.repository

import com.dev_young.note.data.data_source.Dao
import com.dev_young.note.domain.model.Note
import com.dev_young.note.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dao: Dao
    ) : Repository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNote(id: Int): Note {
        return dao.getNote(id)
    }

    override suspend fun upsertNote(note: Note) {
        return dao.upsertNote(note = note)
    }

    override suspend fun deleteNote(note: Note) {
        return dao.deleteNote(note = note)
    }
}