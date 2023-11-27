package groupassigment.taskmanager.application.screens.tasks_list

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import groupassigment.taskmanager.application.model.getTitle
import groupassigment.taskmanager.application.ui.theme.TaskmanagerTheme
import com.notes.app.ui.theme.PurpleGrey40
import groupassigment.taskmanager.application.R
import groupassigment.taskmanager.application.model.Task

@Composable
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun TasksListScreen(
    restartApp: (String) -> Unit,
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TasksListViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) { viewModel.initialize(restartApp) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onAddClick(openScreen) },
                modifier = modifier.padding(16.dp),
                containerColor = Color.LightGray,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Filled.Add, "Add")
            }
        }
    ) {
        val tasks by viewModel.tasks.collectAsState(emptyList())
        var showExitAppDialog by remember { mutableStateOf(false) }
        var showRemoveAccDialog by remember { mutableStateOf(false) }

        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                actions = {
                    IconButton(onClick = { viewModel.onStarClick(openScreen) }) {
                        Icon(Icons.Filled.Star, "See Completed task")
                    }
                    IconButton(onClick = { showExitAppDialog = true }) {
                        Icon(Icons.Filled.ExitToApp, "Exit app")
                    }
                    IconButton(onClick = { showRemoveAccDialog = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.person_remove),
                            contentDescription = "Remove account",
                            tint = PurpleGrey40
                        )
                    }
                }
            )

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                LazyColumn {
                    // Check if any tasks exists
                    if (tasks.isEmpty()) {
                        item {
                            Text(
                                text = "No tasks yet, create one by pressing the + button",
                                modifier = Modifier.padding(12.dp, 12.dp, 12.dp, 12.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                    // Check if no task is marked as not completed
                    if (tasks.none { !it.completed }) {
                        item {
                            Text(
                                text = "All tasks completed, create a new one by pressing the + " +
                                        "button",
                                modifier = Modifier.padding(12.dp, 12.dp, 12.dp, 12.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                    items(tasks, key = { it.id }) { taskItem ->
                        if (!taskItem.completed){
                            TaskItem(
                                task = taskItem,
                                onActionClick = { viewModel.onTaskClick(openScreen, taskItem) }
                            )
                        }

                    }
                }
            }

            if (showExitAppDialog) {
                AlertDialog(
                    title = { Text(stringResource(R.string.sign_out_title)) },
                    text = { Text(stringResource(R.string.sign_out_description)) },
                    dismissButton = {
                        Button(onClick = { showExitAppDialog = false }) {
                            Text(text = stringResource(R.string.cancel))
                        }
                    },
                    confirmButton = {
                        Button(onClick = {
                            viewModel.onSignOutClick()
                            showExitAppDialog = false
                        }) {
                            Text(text = stringResource(R.string.sign_out))
                        }
                    },
                    onDismissRequest = { showExitAppDialog = false }
                )
            }

            if (showRemoveAccDialog) {
                AlertDialog(
                    title = { Text(stringResource(R.string.delete_account_title)) },
                    text = { Text(stringResource(R.string.delete_account_description)) },
                    dismissButton = {
                        Button(onClick = { showRemoveAccDialog = false }) {
                            Text(text = stringResource(R.string.cancel))
                        }
                    },
                    confirmButton = {
                        Button(onClick = {
                            viewModel.onDeleteAccountClick()
                            showRemoveAccDialog = false
                        }) {
                            Text(text = stringResource(R.string.delete_account))
                        }
                    },
                    onDismissRequest = { showRemoveAccDialog = false }
                )
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onActionClick: (String) -> Unit,
) {
    val viewModel = hiltViewModel<TasksListViewModel>()

    Card(
        modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onActionClick(task.id) }
        ) {
            Text(
                text = task.getTitle(),
                modifier = Modifier.padding(12.dp, 12.dp, 12.dp, 12.dp),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = task.dueDate,
                modifier = Modifier.padding(12.dp, 12.dp, 12.dp, 12.dp),
                style = MaterialTheme.typography.bodyLarge
            )
            if (task.completed) {
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = "✅"
                )
            }

        }
        if (!task.completed) {
            Button(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = { viewModel.onSetTaskCompleted(task) })
            {
                Text(text = "Mark as Done")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TaskListPreview() {
    TaskmanagerTheme {
        TasksListScreen({ }, { })
    }
}