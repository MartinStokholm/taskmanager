package groupassigment.taskmanager.application.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.GeoPoint

private const val TITLE_MAX_SIZE = 20

data class Task(
    @DocumentId val id: String = "",
    val name: String = "An important task",
    val description: String = "Description of the task",
    val dueDate: String = "28/11/2023",
    val priority: String = "High",
    val completed: Boolean = false,
    var location: GeoPoint = GeoPoint(56.171700, 10.190870),
    val createdAt: String = "",
    val userId: String = "",
)

fun Task.getTitle(): String {
    val isLongText = this.name.length > TITLE_MAX_SIZE
    val endRange = if (isLongText) TITLE_MAX_SIZE else this.name.length - 1
    return this.name.substring(IntRange(0, endRange))
}
