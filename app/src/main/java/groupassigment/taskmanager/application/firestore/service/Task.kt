package groupassigment.taskmanager.application.firestore.service

data class Task (
    val id: String,
    val name: String,
    val description: String,
    val status: String,
    val userId: String,
    val createdAt: String,
    val location: String
)


