package groupassigment.taskmanager.application

import android.os.Bundle
import android.widget.Toast

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import groupassigment.taskmanager.application.ui.theme.ApplicationTheme
import groupassigment.taskmanager.application.compose.home.Overview
import groupassigment.taskmanager.application.compose.task.TaskDetailsScreen
import groupassigment.taskmanager.application.viewmodels.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import groupassigment.taskmanager.application.auth.GoogleAuthUiClient
import groupassigment.taskmanager.application.compose.home.ModalMenu
import groupassigment.taskmanager.application.compose.task.TaskList
import groupassigment.taskmanager.application.compose.user.RegisterScreen
import groupassigment.taskmanager.application.compose.user.SignInScreen
import groupassigment.taskmanager.application.viewmodels.SignInViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApplicationTheme {
                val scope = rememberCoroutineScope()
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val navController = rememberNavController()
                val vm = viewModel<TaskViewModel>()

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        var currentDestination by remember { mutableStateOf("home") }
                        ModalDrawerSheet {
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = Color.LightGray
                            )
                            {
                                // The navigation drawer with links to navigate to different
                                // destinations in the app
                                ModalMenu(
                                    drawerState = drawerState,
                                    onDestinationClicked = { destination ->
                                        currentDestination = destination
                                    }
                                )
                                // Observe changes to currentDestination and navigate accordingly
                                // from the drawer menu
                                when (currentDestination) {
                                    "taskList" -> navController.navigate("taskList")
                                    "signIn" -> navController.navigate("signIn")
                                    "register" -> navController.navigate("register")
                                }
                            }
                        }
                    },
                    content = {
                        Scaffold(
                            topBar =
                            {
                                ApplicationTopBar(title = "Menu", onMenu = {
                                    if (drawerState.isOpen)
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    else
                                        scope.launch {
                                            drawerState.open()
                                        }
                                })
                            },
                            content =
                            { padding ->
                                Column(
                                    modifier = Modifier.padding(padding)

                                ) {
                                    // NavHost to control which composable gets rendered
                                    NavHost(navController, startDestination = "home") {
                                        // Home view
                                        composable("home") {
                                            val tasks = vm.getTasks().value ?: emptyList()
                                            Overview(
                                                tasks = tasks,
                                                navController = navController
                                            )
                                        }
                                        // Task List view
                                        composable("taskList") {
                                            val tasks = vm.getTasks().value ?: emptyList()
                                            TaskList(
                                                tasks = tasks,
                                                navController = navController
                                            )
                                        }
                                        // Task details view
                                        composable("taskDetails/{taskId}") {
                                            val taskId = it.arguments?.getString("taskId")
                                            TaskDetailsScreen(
                                                task = vm.getTasks().value?.first {
                                                    task -> task.id.toString() == taskId }!!,
                                                navController = navController
                                            )
                                        }
                                        // User sign in view
                                        composable("signIn") {
                                            val viewModel = viewModel<SignInViewModel>()
                                            val state by viewModel.state
                                                .collectAsStateWithLifecycle()

                                            val launcher = rememberLauncherForActivityResult(
                                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                                onResult = { result ->
                                                    if (result.resultCode == RESULT_OK) {
                                                        lifecycleScope.launch {
                                                            val signInResult = googleAuthUiClient
                                                                .signInWithIntent(
                                                                    intent = result.data ?:
                                                                    return@launch
                                                                )
                                                            viewModel.onSignInResult(signInResult)
                                                        }
                                                    }
                                                }
                                            )
                                            LaunchedEffect(key1 = state.isSignInSuccessful) {
                                                if (state.isSignInSuccessful) {
                                                    Toast.makeText(
                                                        applicationContext,
                                                        "Sign in successful",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }
                                            }

                                            SignInScreen(
                                                state = state,
                                                onSignInClick = {
                                                    lifecycleScope.launch {
                                                        val signInIntentSender = googleAuthUiClient
                                                            .signIn()
                                                        launcher.launch(
                                                            IntentSenderRequest.Builder(
                                                                signInIntentSender ?: return@launch
                                                            ).build(
                                                            )
                                                        )
                                                    }
                                                }
                                            )
                                        }

                                        // User register view
                                        composable("register") {
                                            RegisterScreen {
                                                navController.navigate("home")
                                            }
                                        }
                                    }
                                }
                            })
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationTopBar(title: String, onMenu: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = title)
        },colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondary
        ),
        navigationIcon = {
            IconButton(onClick = onMenu) {
                Icon(Icons.Filled.Menu, "menu")
            }
        }
    )
}