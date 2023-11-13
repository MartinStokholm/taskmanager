package groupassigment.taskmanager.application.viewmodels

import androidx.lifecycle.ViewModel
import groupassigment.taskmanager.application.data.SignInResult
import groupassigment.taskmanager.application.data.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel : ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        )}
    }

    fun resetState() {
        _state.update { SignInState() }
    }
}