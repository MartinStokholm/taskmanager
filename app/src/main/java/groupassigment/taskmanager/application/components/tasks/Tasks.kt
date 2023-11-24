package groupassigment.taskmanager.application.components.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import groupassigment.taskmanager.application.firestore.service.FireStore
import groupassigment.taskmanager.application.firestore.service.Task

@Composable
fun Tasks(service: FireStore, nav: NavController) {
    val tasks = remember { mutableStateOf(emptyList<Task>()) }
    LaunchedEffect(Unit) {
        val list = service.getTasks()
        tasks.value = list
    }
    Column() {
        tasks.value.map {
            Column() {
                Row() {
                    Text("Id: ")
                    Text(it.id)
                }
                Row() {
                    Text("Name: ")
                    Text(it.name)
                }
            }
        }
        Button(onClick = { nav.navigate("CreateTask") }) {
            Text("Create Task")
        }
    }
}
