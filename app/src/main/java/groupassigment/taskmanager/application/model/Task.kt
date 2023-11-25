package groupassigment.taskmanager.application.model

import com.google.firebase.firestore.DocumentId

private const val TITLE_MAX_SIZE = 30

data class Task(
    @DocumentId val id: String = "",
    val text: String = "",
    val description: String = "",
    val dueDate: String = "",
    val priority: String = "",
    val status: String = "",
    val createdAt: String = "",
    val userId: String = ""
)

fun Task.getTitle(): String {
    val isLongText = this.text.length > TITLE_MAX_SIZE
    val endRange = if (isLongText) TITLE_MAX_SIZE else this.text.length - 1
    return this.text.substring(IntRange(0, endRange))
}
