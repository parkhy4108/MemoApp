package com.dev_young.note.presentation.notesList

import com.dev_young.note.domain.model.Note


data class MainState (
    val notes: List<Note> = emptyList(),
    val animatedVisible: Boolean = false
)