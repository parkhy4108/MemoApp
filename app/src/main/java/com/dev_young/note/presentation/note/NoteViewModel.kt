package com.dev_young.note.presentation.note

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_young.note.domain.model.Note
import com.dev_young.note.domain.usecase.SaveNote
import com.dev_young.note.domain.usecase.GetNote
import com.dev_young.note.util.SnackBarManager
import com.dev_young.note.R.string as AppText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val saveNote: SaveNote,
    private val getNote: GetNote
) : ViewModel() {

    var state = mutableStateOf(NoteState())
        private set

    private var noteId: Int? = null

    private val title get() = state.value.title
    private val content get() = state.value.content
    private val color get() = state.value.color

    fun loadNote(id: Int) {
        viewModelScope.launch {
            val note = getNote(id)
            noteId = id
            state.value = state.value.copy(
                title = note.title,
                content = note.content,
                color = note.color,
            )
        }
    }

    fun onTitleChanged(newTitle: String) {
        state.value = state.value.copy(title = newTitle)
    }

    fun onContentChanged(newContent: String) {
        state.value = state.value.copy(content = newContent)
    }

    fun onColorChanged(newColor: Int) {
        state.value = state.value.copy(color = newColor)
    }

    fun onColorSectionVisible(colorSection: Boolean) {
        state.value = state.value.copy(colorSection = !colorSection)
    }

    suspend fun onSaveClicked(popUpScreen: () -> Unit) {
        if (title.isNotBlank()) {
            saveNote().join()
            popUpScreen()
        } else SnackBarManager.showMessage(AppText.titleHint)
    }

    private fun saveNote() = viewModelScope.launch(Dispatchers.IO) {
        saveNote(
            Note(
                id = noteId,
                title = title,
                content = content,
                color = color,
                timestamp = System.currentTimeMillis()
            )
        )
    }

    fun onBack(popUpScreen: () -> Unit) = popUpScreen()
}