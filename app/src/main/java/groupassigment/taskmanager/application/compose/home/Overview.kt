package groupassigment.taskmanager.application.compose.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import groupassigment.taskmanager.application.compose.buttons.AddTaskButton
import groupassigment.taskmanager.application.compose.task.TaskItem
import groupassigment.taskmanager.application.data.Task

@Composable
fun Overview (
   tasks: List<Task>,
   navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Upcoming Tasks",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyColumn {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onClick = {
                        navController.navigate("taskDetails/${task.id}")
                    }
                )
            }
        }
        // Add Task Floating Action Button
        AddTaskButton(onClick = {
            // Handle click to add a new task
        })
    }
}




