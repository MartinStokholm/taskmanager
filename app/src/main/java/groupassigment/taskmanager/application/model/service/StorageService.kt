package groupassigment.taskmanager.application.model.service

import groupassigment.taskmanager.application.model.Task
import kotlinx.coroutines.flow.Flow

interface StorageService {
    val tasks: Flow<List<Task>>
    suspend fun createTask(task: Task)
    suspend fun readTask(taskId: String): Task?
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(taskId: String)
}
