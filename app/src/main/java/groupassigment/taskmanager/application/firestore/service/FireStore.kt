package groupassigment.taskmanager.application.firestore.service

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FireStore(private val api: FirebaseFirestore, private val auth: FirebaseAuth) {
    companion object {
        const val TAG = "FIRE_STORE_SERVICE"
    }

    suspend fun getTasks(): List<Task> {
        return suspendCoroutine { continuation ->
            api.collection("Horses")
                .get()
                .addOnSuccessListener {
                    val tasks =
                        it.documents.map { d -> Task(
                            d.id,
                            d.data?.get("Name").toString(),
                            d.data?.get("Description").toString(),
                            d.data?.get("Status").toString(),
                            d.data?.get("UserId").toString(),
                            d.data?.get("CreatedAt").toString(),
                            d.data?.get("Location").toString())
                        }
                    continuation.resume(tasks)
                }
                .addOnFailureListener {
                    Log.v(TAG, "We failed $it")
                    throw it
                }
            }
    }

    suspend fun createTask(name: String) {
        val task = hashMapOf("Name" to name)
        suspendCoroutine { continuation ->
            api.collection("Tasks")
                .add(task)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    continuation.resume(documentReference.id)
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                    throw e
                }
        }
    }

    suspend fun signup(email: String, password: String) {
        suspendCoroutine { continuation ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser ?: throw Exception("Something wrong")
                        val signedInUser = user.email?.let { User(user.providerId, it) }
                            ?: throw Exception("createUserWithEmail:$email failure")
                        continuation.resume(signedInUser)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        throw throw Exception("createUserWithEmail: $email failure", task.exception)
                    }
                }
        }
    }
    suspend fun login(email: String, password: String) {
        suspendCoroutine { continuation ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser ?: throw Exception("Something wrong")
                        val signedInUser = user.email?.let { User(user.providerId, it) }
                            ?: throw Exception("createUserWithEmail:$email failure")
                        continuation.resume(signedInUser)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        throw throw Exception("createUserWithEmail: $email failure", task.exception)
                    }
                }
        }
    }
}