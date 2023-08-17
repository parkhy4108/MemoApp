package com.dev_young.note.presentation.notesList

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev_young.note.ui.theme.Color0
import kotlinx.coroutines.launch
import com.dev_young.note.R.drawable as AppImg
import com.dev_young.note.R.string as AppText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    snackBarHostState: SnackbarHostState,
    openScreen: (String) -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
    val state by viewModel.state
    val scope = rememberCoroutineScope()
    val titleColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    val containerColor = if (isSystemInDarkTheme()) darkColorScheme().background else Color0
    val configuration = LocalConfiguration.current
    val columnCount = when (configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> 3
        else -> 4
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.addButtonClick(openScreen) },
                shape = CircleShape
            ) {
                Icon(painter = painterResource(id = AppImg.ic_add), contentDescription = null)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(containerColor)
                .padding(paddingValues)
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = AppText.MyNote),
                    style = MaterialTheme.typography.titleLarge,
                    fontFamily = FontFamily.Cursive,
                    color = titleColor
                )
            }
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(columnCount),
                contentPadding = PaddingValues(vertical = 15.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(state.notes) { note ->

                    NoteCard(
                        note = note,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .aspectRatio(1f)
                            .clickable { note.id?.let { viewModel.noteClick(it, openScreen) } },
                        onDeleteClick = {
                            viewModel.deleteButtonClick(note = note)
                            scope.launch {
                                val result = snackBarHostState.showSnackbar(
                                    message = "삭제되었습니다",
                                    actionLabel = "취소",
                                    duration = SnackbarDuration.Long
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.undoClick()
                                }
                            }
                        }
                    )
                }
            }
        }
    }

}