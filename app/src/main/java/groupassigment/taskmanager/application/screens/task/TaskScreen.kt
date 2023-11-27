package groupassigment.taskmanager.application.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import groupassigment.taskmanager.application.model.Task
import groupassigment.taskmanager.application.model.getTitle

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TaskScreen(
    taskId: String,
    popUpScreen: () -> Unit,
    openScreen: (String) -> Unit,
    restartApp: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel = hiltViewModel(),
) {
    val task = viewModel.task.collectAsState()

    LaunchedEffect(Unit) { viewModel.initialize(taskId, restartApp) }

    Column(modifier = Modifier
        .background(Color.White) // Set your desired background color
        // here
        .fillMaxWidth()
        .fillMaxHeight()) {
        TopAppBar(
            title = { Text(task.value.getTitle()) },
            actions = {
                IconButton(onClick = { viewModel.saveTask(popUpScreen)}) {
                    Icon(Icons.Filled.ArrowBack, "Go back")
                }
                IconButton(onClick = { viewModel.onEditClick(openScreen, task.value)}) {
                    Icon(Icons.Filled.Edit, "Edit task")
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
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally) {
            if (task.value.completed) {
                Text(
                    text = "✅",
                    modifier = Modifier
                        .padding(12.dp, 12.dp, 0.dp, 12.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                if (task.value.priority == "High") {
                    Text(
                        text = "‼️‼️",
                        modifier = Modifier
                            .padding(12.dp, 12.dp, 0.dp, 12.dp),
                        style = MaterialTheme.typography.bodyLarge)
                }
                if (task.value.priority == "Medium") {
                    Text(text = "‼️",
                        modifier = Modifier
                            .padding(12.dp, 12.dp, 0.dp, 12.dp),
                        style = MaterialTheme.typography.bodyLarge)
                }
                if (task.value.priority == "Low") {
                    Text(text = "❗",
                        modifier = Modifier
                            .padding(12.dp, 12.dp, 0.dp, 12.dp),
                        style = MaterialTheme.typography.bodyLarge)
                }
            }
            Text(
                text = "${task.value.name}",
                modifier = Modifier
                    .padding(12.dp, 12.dp, 0.dp, 12.dp),
                style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = "⭐ What to do ⭐",
                modifier = Modifier
                    .padding(12.dp, 12.dp, 12.dp, 12.dp),
                style = MaterialTheme.typography.bodyLarge)
            Text(
                text = " ${task.value.description}",
                modifier = Modifier
                    .padding(12.dp, 12.dp, 12.dp, 12.dp),
                style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "⏰ When to do ⏰",
                modifier = Modifier
                    .padding(12.dp, 12.dp, 12.dp, 12.dp),
                style = MaterialTheme.typography.bodyLarge)
            Text(
                text = " ${task.value.dueDate}",
                modifier = Modifier
                    .padding(12.dp, 12.dp, 12.dp, 12.dp),
                style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "📌 Where to do📌",
                modifier = Modifier
                    .padding(12.dp, 12.dp, 12.dp, 12.dp),
                style = MaterialTheme.typography.bodyLarge)

            DisplayOnMap(task.value)
            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = "Created on: ${task.value.createdAt}",
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .wrapContentHeight()
            )
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}


@Composable
fun DisplayOnMap(task: Task) {
    val taskLocation = LatLng(task.location.latitude, task.location.longitude)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(taskLocation, 10f)
    }
    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .height(300.dp),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = taskLocation),
            title = "Task Location",
            snippet = "Marker at task location "
        )
    }
}
