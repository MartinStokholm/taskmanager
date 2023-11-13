package groupassigment.taskmanager.application.data

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val userId: String,
    val username: String,
    val profilePictureUrl: String?
)