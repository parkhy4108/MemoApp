package com.dev_young.note

import android.content.res.Resources
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dev_young.note.util.SnackBarManager
import com.dev_young.note.util.SnackBarMessage.Companion.toMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@Composable
fun rememberAppState(
    snackBarHostState: SnackbarHostState = remember{ SnackbarHostState() },
    navController: NavHostController = rememberNavController(),
    resources: Resources = resources(),
    snackBarManager: SnackBarManager = SnackBarManager,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(snackBarHostState, navController, snackBarManager, resources, coroutineScope) {
    AppState(snackBarHostState, navController, snackBarManager, resources, coroutineScope)
}

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

@Stable
class AppState constructor(
    val snackBarHostState: SnackbarHostState,
    val navController: NavHostController,
    private val snackBarManager: SnackBarManager,
    private val resources: Resources,
    coroutineScope: CoroutineScope
) {

    init {
        coroutineScope.launch {
            snackBarManager.snackMessage.filterNotNull().collect { snackBarMessage ->
                val text = snackBarMessage.toMessage(resources)
                snackBarHostState.showSnackbar(text)
                snackBarManager.initMessage()
            }
        }
    }

    fun popUp() {
        navController.popBackStack()
    }

    fun navigate(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
        }
    }

}
