package groupassigment.taskmanager.application.screens.profile

import dagger.hilt.android.lifecycle.HiltViewModel
import groupassigment.taskmanager.application.SPLASH_SCREEN
import groupassigment.taskmanager.application.TASK_COMPLETED_SCREEN
import groupassigment.taskmanager.application.model.service.AccountService
import groupassigment.taskmanager.application.model.service.StorageService
import groupassigment.taskmanager.application.screens.TaskmanagerAppViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val accountService: AccountService,
) : TaskmanagerAppViewModel(){
    fun initialize(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.currentUser.collect { user ->
                if (user == null) restartApp(SPLASH_SCREEN)
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