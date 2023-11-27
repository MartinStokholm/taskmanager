package groupassigment.taskmanager.application

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import groupassigment.taskmanager.application.screens.task.TaskScreen
import groupassigment.taskmanager.application.screens.tasks_list.TasksListScreen
import groupassigment.taskmanager.application.screens.sign_in.SignInScreen
import groupassigment.taskmanager.application.screens.sign_up.SignUpScreen
import groupassigment.taskmanager.application.screens.splash.SplashScreen
import groupassigment.taskmanager.application.screens.task_completed.TasksCompletedScreen
import groupassigment.taskmanager.application.screens.task_edit.TaskEditScreen
import groupassigment.taskmanager.application.ui.theme.TaskmanagerTheme

@Composable
fun TaskmanagerApp() {
    TaskmanagerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val appState = rememberAppState()

            Scaffold { innerPaddingModifier ->
                NavHost(
                    navController = appState.navController,
                    startDestination = SPLASH_SCREEN,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    notesGraph(appState)
                }
            }
        }
    }
}

@Composable
fun rememberAppState(navController: NavHostController = rememberNavController()) =
    remember(navController) {
        TaskmanagerAppState(navController)
    }

fun NavGraphBuilder.notesGraph(appState: TaskmanagerAppState) {
    composable(TASK_LIST_SCREEN) {
        TasksListScreen(
            restartApp = { route -> appState.clearAndNavigate(route) },
            openScreen = { route -> appState.navigate(route) }
        )
    }
    composable(TASK_COMPLETED_SCREEN)
    {
        TasksCompletedScreen(
            restartApp = { route -> appState.clearAndNavigate(route) },
            openScreen = { route -> appState.navigate(route) },
            popUpScreen = { appState.popUp() }
        )
    }
    composable(
        route ="$TASK_EDIT_SCREEN$TASK_ID_ARG",
        arguments = listOf(navArgument(TASK_ID) { defaultValue = TASK_DEFAULT_ID })
    ) {
        TaskEditScreen(
            taskId = it.arguments?.getString(TASK_ID) ?: TASK_DEFAULT_ID,
            popUpScreen = { appState.popUp() },
            restartApp = { route -> appState.clearAndNavigate(route) }
        )
    }

    composable(
        route = "$TASK_SCREEN$TASK_ID_ARG",
        arguments = listOf(navArgument(TASK_ID) { defaultValue = TASK_DEFAULT_ID })
    ) {
        TaskScreen(
            taskId = it.arguments?.getString(TASK_ID) ?: TASK_DEFAULT_ID,
            popUpScreen = { appState.popUp() },
            openScreen = { route: String -> appState.navigate(route) },
            restartApp = { route -> appState.clearAndNavigate(route) }
        )
    }

    composable(SIGN_IN_SCREEN) {
        SignInScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(SIGN_UP_SCREEN) {
        SignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }
    
    composable(SPLASH_SCREEN) {
        SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }
}
