package groupassigment.taskmanager.application.model

import com.google.firebase.firestore.DocumentId

private const val TITLE_MAX_SIZE = 30

data class Task(
    @DocumentId val id: String = "",
    val name: String = "",
    val description: String = "",
    val dueDate: String = "",
    val priority: String = "",
    val isDone: Boolean = false,
    val createdAt: String = "",
    val lat: Double = 42.0,
    val lng: Double = 69.0,
    val userId: String = ""
)

fun Task.getTitle(): String {
    val isLongText = this.name.length > TITLE_MAX_SIZE
    val endRange = if (isLongText) TITLE_MAX_SIZE else this.name.length - 1
    return this.name.substring(IntRange(0, endRange))
}

fun Task.getIsDone(): String {
    return if (this.isDone) "✅" else "❌"
}
