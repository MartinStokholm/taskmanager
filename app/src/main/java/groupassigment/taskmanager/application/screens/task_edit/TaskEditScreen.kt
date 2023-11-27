package groupassigment.taskmanager.application.screens.task_edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import groupassigment.taskmanager.application.model.getTitle
import groupassigment.taskmanager.application.screens.task.TaskViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TaskEditScreen(
    taskId: String,
    popUpScreen: () -> Unit,
    restartApp: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel = hiltViewModel()
) {
    val task = viewModel.task.collectAsState()
    LaunchedEffect(Unit) { viewModel.initialize(taskId, restartApp) }

    Column(modifier = Modifier
        .background(Color.White) // Set your desired background color here
        .fillMaxWidth()
        .fillMaxHeight()) {
        TopAppBar(
            title = { Text(task.value.getTitle()) },
            actions = {
                IconButton(onClick = { viewModel.saveTask(popUpScreen) }) {
                    Icon(Icons.Filled.Done, "Save task")
                }
                IconButton(onClick = { viewModel.deleteTask(popUpScreen) }) {
                    Icon(Icons.Filled.Delete, "Delete task")
                }
            }
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Column(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EditableTextField("Name", task.value.name) { viewModel.updateName(it)  }
            EditableTextField("Priority", task.value.priority) { viewModel.updatePriority(it) }
            EditableTextField("Description", task.value.description) { viewModel.updateDescription(it) }
            EditableTextField("Due Date", task.value.dueDate) { viewModel.updateDueDate(it) }
            DoubleInputField(
                label = "Latitude",
                value = task.value.location.latitude,
                onValueChange = { viewModel.updateLocationLat(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp))
            DoubleInputField(
                label = "Longitude",
                value = task.value.location.longitude,
                onValueChange = { viewModel.updateLocationLong(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp))
        }
    }
}

@Composable
private fun EditableTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

@Composable
fun DoubleInputField(
    label: String,
    value: Double,
    onValueChange: (Double) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value.toString(),
        onValueChange = {
            val doubleValue = it.toDoubleOrNull() ?: 0.0
            onValueChange(doubleValue)
        },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        modifier = modifier
    )
}
