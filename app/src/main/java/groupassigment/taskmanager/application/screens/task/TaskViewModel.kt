package groupassigment.taskmanager.application.screens.task

import groupassigment.taskmanager.application.TASK_DEFAULT_ID
import groupassigment.taskmanager.application.SPLASH_SCREEN
import groupassigment.taskmanager.application.model.service.AccountService
import groupassigment.taskmanager.application.model.service.StorageService
import groupassigment.taskmanager.application.screens.TaskmanagerAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import groupassigment.taskmanager.application.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService
) : TaskmanagerAppViewModel() {
    val task = MutableStateFlow(DEFAULT_TASK)

    fun initialize(taskId: String, restartApp: (String) -> Unit) {
        launchCatching {
            task.value = storageService.readTask(taskId) ?: DEFAULT_TASK
        }

        observeAuthenticationState(restartApp)
    }

    private fun observeAuthenticationState(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.currentUser.collect { user ->
                if (user == null) restartApp(SPLASH_SCREEN)
            }
        }
    }

    fun updateTask(newText: String) {
        task.value = task.value.copy(text = newText)
    }

    fun saveTask(popUpScreen: () -> Unit) {
        launchCatching {
            if (task.value.id == TASK_DEFAULT_ID) {
                storageService.createTask(task.value)
            } else {
                storageService.updateTask(task.value)
            }
        }

        popUpScreen()
    }

    fun deleteTask(popUpScreen: () -> Unit) {
        launchCatching {
            storageService.deleteTask(task.value.id)
        }

        popUpScreen()
    }

    companion object {
        private val DEFAULT_TASK = Task(TASK_DEFAULT_ID, "My Task", "My Description", "25-11-2023",
            "HIGH", "NEW", "Today")
    }
}