package groupassigment.taskmanager.application.components.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import groupassigment.taskmanager.application.firestore.service.FireStore
import kotlinx.coroutines.launch

@Composable
fun Signup(service: FireStore, nav: NavController) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    Column() {
        Row() {
            Text("Email:")
            TextField(value = email.value, onValueChange = { newText -> email.value = newText })
        }
        Row() {
            Text("Password:")
            TextField(
                value = email.value,
                onValueChange = { newText -> email.value = newText },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }
        Button(onClick = {
            scope.launch {
                val user=service.signup(email.value,password.value)

            }
        }) {
            Text("Signup")
        }
    }
}