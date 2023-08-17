package com.dev_young.note.domain.usecase

import com.dev_young.note.domain.model.Note
import com.dev_young.note.domain.repository.Repository
import javax.inject.Inject

class SaveNote @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(note: Note) {
        return repository.upsertNote(note)
    }
}