package groupassigment.taskmanager.application.screens.profile

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import groupassigment.taskmanager.application.SPLASH_SCREEN
import groupassigment.taskmanager.application.TASK_COMPLETED_SCREEN
import groupassigment.taskmanager.application.model.ChuckNorrisJoke
import groupassigment.taskmanager.application.model.service.AccountService
import groupassigment.taskmanager.application.model.service.ChuckNorrisJokesService
import groupassigment.taskmanager.application.screens.TaskmanagerAppViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val chuckNorrisJokesService: ChuckNorrisJokesService,
    private val accountService: AccountService,
) : TaskmanagerAppViewModel(){
    fun initialize(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.currentUser.collect { user ->
                if (user == null) restartApp(SPLASH_SCREEN)
            }
        }
        getJoke()
    }

    private val _chuckNorrisJoke = mutableStateOf<ChuckNorrisJoke?>(null)
    val chuckNorrisJoke: State<ChuckNorrisJoke?> = _chuckNorrisJoke

    fun getJoke() {
        viewModelScope.launch {
            try {
                _chuckNorrisJoke.value = chuckNorrisJokesService.getRandomJoke()
            } catch (e: Exception) {
                Log.d("ProfileViewModel", "failed to fetch joke, error: ${e.message}")
            }
        }
    }

    fun getUserEmail() = accountService.currentUserEmail

    fun onStarClick(openScreen: (String) -> Unit) {
        openScreen(TASK_COMPLETED_SCREEN)
    }

    fun onSignOutClick() {
        launchCatching {
            accountService.signOut()
        }
    }

    fun onDeleteAccountClick() {
        launchCatching {
            accountService.deleteAccount()
        }
    }
}