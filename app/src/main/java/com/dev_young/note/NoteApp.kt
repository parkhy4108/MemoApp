package com.dev_young.note

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dev_young.note.presentation.note.NoteScreen
import com.dev_young.note.presentation.notesList.MainScreen
import com.dev_young.note.ui.theme.NoteTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteApp() {
    NoteTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF1B1B1A)
        ) {
            val appState = rememberAppState()
            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = appState.snackBarHostState,
                        modifier = Modifier,
                        snackbar = { snackBarData ->
                            Snackbar(snackBarData)
                        }
                    )
                }
            ) { innerPadding ->
                NavHost(
                    navController = appState.navController,
                    startDestination = Screen.Main.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    graph(appState)
                }
            }
        }
    }
}

fun NavGraphBuilder.graph(appState: AppState) {
    composable(route = Screen.Main.route) {
        MainScreen(
            snackBarHostState = appState.snackBarHostState,
            openScreen = { route -> appState.navigate(route) }
        )
    }
    composable(
        route = Screen.Note.route,
        arguments = listOf(
            navArgument(ARG_ID) {
                type = NavType.IntType
                defaultValue = -1
            }
        )
    ) {
        val id = it.arguments?.getInt(ARG_ID)!!
        NoteScreen(
            id = id,
            popUpScreen = { appState.popUp() }
        )
    }
}