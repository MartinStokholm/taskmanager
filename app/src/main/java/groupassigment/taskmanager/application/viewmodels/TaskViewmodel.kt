package groupassigment.taskmanager.application.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import groupassigment.taskmanager.application.data.Repository
import groupassigment.taskmanager.application.data.Task
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {
    private val taskList = MutableLiveData<List<Task>>()

    init {
        val tasks = listOf(
            Task(
                id = 1,
                name = "Task 1",
                description = "This is a sample task",
                dueDate = "2021-10-1",
                isCompleted = "false"
            ),
            Task(
                id = 2,
                name = "Task 2",
                description = "This is a sample task",
                dueDate = "2021-10-2",
                isCompleted = "false"
            ),
            Task(
                id = 3,
                name = "Task 3",
                description = "This is a sample task",
                dueDate = "2021-10-3",
                isCompleted = "false"
            ),
            Task(
                id = 4,
                name = "Task 4",
                description = "This is a sample task",
                dueDate = "2021-10-4",
                isCompleted = "false"
            ),
            Task(
                id = 5,
                name = "Task 5",
                description = "This is a sample task",
                dueDate = "2021-10-5",
                isCompleted = "false"
            ),
        )
        taskList.value = tasks
    }

    fun getTasks(): LiveData<List<Task>> {
        return taskList
    }

    fun setTaskIsCompleted(id: Int, isCompleted: Boolean) {
        val tasks = taskList.value?.toMutableList()
        val task = tasks?.find { it.id == id }
        task?.isCompleted = isCompleted.toString()
        taskList.value = tasks
    }

    fun addTask(task: Task) {
        val tasks = taskList.value?.toMutableList()
        tasks?.add(task)
        taskList.value = tasks
    }

    fun getTaskById(id: Int): Task? {
        return taskList.value?.find { it.id == id }
    }
}