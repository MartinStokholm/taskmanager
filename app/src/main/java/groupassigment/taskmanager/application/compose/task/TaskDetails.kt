package groupassigment.taskmanager.application.compose.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import groupassigment.taskmanager.application.compose.buttons.GoBackButton
import groupassigment.taskmanager.application.data.Task

@Composable
fun TaskDetailsScreen(
    task: Task,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Task Details", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Title: ${task.name}", style = MaterialTheme.typography.titleMedium)
        Text(text = "Due Date: ${task.dueDate}", style = MaterialTheme.typography.titleSmall)
        GoBackButton(onClick = {
            navController.popBackStack()
        })
    }
}