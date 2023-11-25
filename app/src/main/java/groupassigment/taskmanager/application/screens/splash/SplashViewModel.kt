package groupassigment.taskmanager.application.screens.splash

import groupassigment.taskmanager.application.TASK_LIST_SCREEN
import groupassigment.taskmanager.application.SIGN_IN_SCREEN
import groupassigment.taskmanager.application.SPLASH_SCREEN
import groupassigment.taskmanager.application.model.service.AccountService
import groupassigment.taskmanager.application.screens.TaskmanagerAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
  private val accountService: AccountService
) : TaskmanagerAppViewModel() {

  fun onAppStart(openAndPopUp: (String, String) -> Unit) {
    if (accountService.hasUser()) openAndPopUp(TASK_LIST_SCREEN, SPLASH_SCREEN)
    else openAndPopUp(SIGN_IN_SCREEN, SPLASH_SCREEN)
  }
}
