package groupassigment.taskmanager.application.data

data class Task(
    val id: Int,
    var name: String,
    var description: String,
    val dueDate: String,
    var isCompleted: String
)
