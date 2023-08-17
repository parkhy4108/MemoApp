package com.dev_young.note.domain.usecase

data class UseCases(
    val getNotes: GetNotes,
    val getNote: GetNote,
    val saveNote: SaveNote,
    val deleteNote: DeleteNote
)
