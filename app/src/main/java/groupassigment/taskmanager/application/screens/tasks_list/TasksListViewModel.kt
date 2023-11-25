package groupassigment.taskmanager.application.screens.tasks_list

import groupassigment.taskmanager.application.TASK_DEFAULT_ID
import groupassigment.taskmanager.application.TASK_ID
import groupassigment.taskmanager.application.TASK_SCREEN
import groupassigment.taskmanager.application.SPLASH_SCREEN
import groupassigment.taskmanager.application.model.service.AccountService
import groupassigment.taskmanager.application.model.service.StorageService
import groupassigment.taskmanager.application.screens.TaskmanagerAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import groupassigment.taskmanager.application.model.Task
import javax.inject.Inject

@HiltViewModel
class TasksListViewModel @Inject constructor(
    private val accountService: AccountService,
    storageService: StorageService
) : TaskmanagerAppViewModel() {
    val tasks = storageService.tasks

    fun initialize(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.currentUser.collect { user ->
                if (user == null) restartApp(SPLASH_SCREEN)
            }
        }
    }
    fun onAddClick(openScreen: (String) -> Unit) {
        openScreen("$TASK_SCREEN?$TASK_ID=$TASK_DEFAULT_ID")
    }

    fun onTaskClick(openScreen: (String) -> Unit, task: Task) {
        openScreen("$TASK_SCREEN?$TASK_ID=${task.id}")
    }

    fun onSignOutClick() {
        launchCatching {
            accountService.signOut()
        }
    }

    fun onDeleteAccountClick() {
        launchCatching {
            accountService.deleteAccount()
        }
    }
}