package com.dev_young.note.presentation.note

import androidx.compose.ui.graphics.toArgb
import com.dev_young.note.ui.theme.Color0

data class NoteState (
    val title: String = "",
    val content : String = "",
    val color: Int = Color0.toArgb(),
    val colorSection: Boolean = true
)