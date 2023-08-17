package com.dev_young.note.presentation.note

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev_young.note.ui.theme.Color0
import com.dev_young.note.ui.theme.Color1
import com.dev_young.note.ui.theme.Color2
import com.dev_young.note.ui.theme.Color3
import com.dev_young.note.ui.theme.Color4
import com.dev_young.note.ui.theme.Color5
import com.dev_young.note.ui.theme.Color6
import com.dev_young.note.ui.theme.Color7
import com.dev_young.note.ui.theme.Color8
import com.dev_young.note.ui.theme.Color9
import com.dev_young.note.util.addFocusCleaner
import kotlinx.coroutines.launch
import com.dev_young.note.R.drawable as AppImg
import com.dev_young.note.R.string as AppText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    id: Int,
    popUpScreen: () -> Unit,
    viewModel: NoteViewModel = hiltViewModel()
) {
    val state by viewModel.state
    val noteColorsList0 =
        listOf(Color0, Color1, Color2, Color3, Color4, Color5, Color6, Color7, Color8, Color9)
    val focusManager = LocalFocusManager.current
    val view = LocalView.current
    val window = (view.context as Activity).window
    val darkTheme = isSystemInDarkTheme()
    val configuration = LocalConfiguration.current
    val columnCount = when (configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> 5
        else -> 10
    }
    val noteBackgroundAnimation = remember {
        Animatable(Color(state.color))
    }
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        if (id != -1) {
            viewModel.loadNote(id)
        }
    }
    LaunchedEffect(state.color) {
        window.statusBarColor = state.color
        window.navigationBarColor = state.color
    }
    DisposableEffect(Unit) {
        onDispose {
            window.statusBarColor =
                if (!darkTheme) Color0.toArgb() else darkColorScheme().background.toArgb()
            window.navigationBarColor =
                if (!darkTheme) Color0.toArgb() else darkColorScheme().background.toArgb()
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { scope.launch { viewModel.onSaveClicked(popUpScreen) } },
                shape = CircleShape
            ) {
                Icon(painter = painterResource(id = AppImg.ic_save), contentDescription = null)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(state.color))
                .addFocusCleaner(focusManager)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { viewModel.onBack(popUpScreen) }) {
                    Icon(
                        painter = painterResource(id = AppImg.ic_back),
                        contentDescription = null,
                        tint = if (state.color == Color(0xFF1B1B1A).toArgb()) Color.White else Color.Black
                    )
                }
                TextButton(onClick = { viewModel.onColorSectionVisible(state.colorSection) }) {
                    Text(
                        text = stringResource(id = AppText.color),
                        color = if (state.color == Color(0xFF1B1B1A).toArgb()) Color.White else Color.Black
                    )
                }
            }
            AnimatedVisibility(
                visible = state.colorSection,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxWidth(),
                        columns = GridCells.Fixed(columnCount),
                        contentPadding = PaddingValues(10.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        items(noteColorsList0) { color ->
                            val colorRGB = color.toArgb()
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .clip(CircleShape)
                                    .background(color = color)
                                    .border(
                                        width = 2.dp,
                                        color = if (state.color == colorRGB) Color.Black
                                        else Color.Transparent,
                                        shape = CircleShape
                                    )
                                    .clickable {
                                        viewModel.onColorChanged(colorRGB)
                                    }
                            )
                        }
                    }
                }
            }
            Divider(color = Color.LightGray)
            val fontColor = if (state.color == Color9.toArgb()) Color.White else Color.Black
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                value = state.title,
                onValueChange = { viewModel.onTitleChanged(it) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle.Default.copy(color = fontColor),
                placeholder = { Text(stringResource(id = AppText.title)) },
                singleLine = true
            )
            Divider(color = Color.LightGray)
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                value = state.content,
                onValueChange = { viewModel.onContentChanged(it) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle.Default.copy(color = fontColor),
                placeholder = { Text(stringResource(id = AppText.content)) }
            )
        }
    }
}