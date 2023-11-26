package groupassigment.taskmanager.application.screens.task

import android.util.Log
import groupassigment.taskmanager.application.TASK_DEFAULT_ID
import groupassigment.taskmanager.application.SPLASH_SCREEN
import groupassigment.taskmanager.application.model.service.AccountService
import groupassigment.taskmanager.application.model.service.StorageService
import groupassigment.taskmanager.application.screens.TaskmanagerAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import groupassigment.taskmanager.application.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import java.text.SimpleDateFormat
import java.util.Locale
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

    fun saveTask(popUpScreen: () -> Unit) {
        launchCatching {
            if (task.value.id == TASK_DEFAULT_ID) {
                storageService.createTask(task.value)
            } else {
                Log.d("TaskViewModel", "saveTask: ${task.value}")
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

    fun updateTaskName(newText: String) {
        task.value = task.value.copy(name = newText)
    }

    fun updateTaskDescription(newText: String) {
        task.value = task.value.copy(description = newText)
    }

    fun setTaskIsDone(it: Boolean) {
        task.value = task.value.copy(isDone = it)
    }

    fun updateTaskPriority(it: String) {
        task.value = task.value.copy(priority = it)
    }

    companion object {
        private fun getCreatedAt(): String {
            val currentTimeMillis = System.currentTimeMillis()
            val date = java.util.Date(currentTimeMillis)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            return dateFormat.format(date)
        }

        private val DEFAULT_TASK = Task(
            id = TASK_DEFAULT_ID,
            createdAt = getCreatedAt(),
            description = "I really need to to this thing at this place",
            isDone = false,
            dueDate = " 25-11-2023",
            lat = 42.0,
            lng = 69.0,
            priority = "MEDIUM",
            name = "Important task",
        )
    }
}