package groupassigment.taskmanager.application.compose.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import groupassigment.taskmanager.application.compose.buttons.NavButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalMenu(drawerState: DrawerState, onDestinationClicked: (String) -> Unit) {
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {

        // Title
        Text(
            text = "Task Manager",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        // Navigate to Overview/home
        NavButton(
            icon = Icons.Default.Home,
            text = "Home",
            onClick = {
                scope.launch {
                    drawerState.close()
                    onDestinationClicked("home")
                }
            }
        )
        // Navigate to Task List
        NavButton(
            icon = Icons.Default.Edit,
            text = "Task List",
            onClick = {
                scope.launch {
                    drawerState.close()
                    onDestinationClicked("taskList")
                }
            }
        )
        // Navigate to SignIn
        NavButton(
            icon = Icons.Default.Person,
            text = "Sign In",
            onClick = {
                scope.launch {
                    drawerState.close()
                    onDestinationClicked("signIn")
                }
            }
        )
        // Navigate to Register
        NavButton(
            icon = Icons.Default.Person,
            text = "Register",
            onClick = {
                scope.launch {
                    drawerState.close()
                    onDestinationClicked("register")
                }
            }
        )
        // Close navigation drawer
        NavButton(
            icon = Icons.Default.Close,
            text = "Close",
            onClick = {
                scope.launch {
                    drawerState.close()
                }
            }
        )
    }
}