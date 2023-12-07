package groupassigment.taskmanager.application.screens.task_completed

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import groupassigment.taskmanager.application.R
import groupassigment.taskmanager.application.screens.tasks_list.TaskItem
import groupassigment.taskmanager.application.screens.tasks_list.TasksListViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun TasksCompletedScreen(
    restartApp: (String) -> Unit,
    openScreen: (String) -> Unit,
    popUpScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TasksListViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) { viewModel.initialize(restartApp) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { popUpScreen() },
                modifier = modifier.padding(16.dp),
                containerColor = Color.LightGray,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Filled.ArrowBack, "Go back")
            }
        }
    ) {
        val tasks by viewModel.tasks.collectAsState(emptyList())
        var tasksCompleted = tasks.filter { it.completed }

        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
                TopAppBar(
                    title = { Text("${tasksCompleted.count() } Completed tasks") },
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
                    items(tasks, key = { it.id }) { taskItem ->
                        if (taskItem.completed) {
                            TaskItem(
                                task = taskItem,
                                onActionClick = { viewModel.onTaskClick(openScreen, taskItem) }
                            )
                        }
                    }
                 }
            }
        }
    }
}



