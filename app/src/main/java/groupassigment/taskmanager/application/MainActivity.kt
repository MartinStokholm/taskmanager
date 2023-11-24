package groupassigment.taskmanager.application

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import groupassigment.taskmanager.application.components.tasks.CreateTask
import groupassigment.taskmanager.application.components.auth.Login
import groupassigment.taskmanager.application.components.auth.Signup
import groupassigment.taskmanager.application.components.tasks.Tasks
import groupassigment.taskmanager.application.firestore.service.FireStore
import groupassigment.taskmanager.application.ui.theme.ApplicationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val auth = Firebase.auth
        FirebaseApp.initializeApp(this);
        val db = FirebaseFirestore.getInstance()
        val service = FireStore(db, auth)
        //madpassword
        auth.currentUser
        setContent {
            ApplicationTheme {
                val navController = rememberNavController()

                // A surface container using the 'background' color from the theme
                androidx.compose.material.Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(navController = navController, startDestination = "Login") {
                        composable("Tasks") { Tasks(service, nav = navController) }
                        composable("CreateTask") { CreateTask(service, nav = navController) }
                        composable("Signup") { Signup(service, nav = navController) }
                        composable("Login") { Login(service, nav = navController) }
                    }

                }
            }
        }
    }
}