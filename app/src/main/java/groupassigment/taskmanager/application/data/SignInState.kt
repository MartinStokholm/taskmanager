package groupassigment.taskmanager.application.data

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,
)
