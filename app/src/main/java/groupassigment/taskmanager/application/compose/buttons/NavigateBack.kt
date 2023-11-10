package groupassigment.taskmanager.application.compose.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GoBackButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
    }
}

@Composable
fun AddTaskButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null)
    }
}