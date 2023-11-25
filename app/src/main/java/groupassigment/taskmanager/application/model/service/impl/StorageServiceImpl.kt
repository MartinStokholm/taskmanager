package groupassigment.taskmanager.application.model.service.impl

import com.google.firebase.firestore.ktx.dataObjects
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import groupassigment.taskmanager.application.model.Task
import groupassigment.taskmanager.application.model.service.AccountService
import groupassigment.taskmanager.application.model.service.StorageService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(private val auth: AccountService) : StorageService {

    @OptIn(ExperimentalCoroutinesApi::class)
    override val tasks: Flow<List<Task>>
        get() =
            auth.currentUser.flatMapLatest { note ->
                Firebase.firestore
                    .collection(TASKS_COLLECTION)
                    .whereEqualTo(USER_ID_FIELD, note?.id)
                    .dataObjects()
            }

    override suspend fun createTask(task: Task) {
        val taskWithUserId = task.copy(userId = auth.currentUserId)
        Firebase.firestore
            .collection(TASKS_COLLECTION)
            .add(taskWithUserId).await()
    }

    override suspend fun readTask(taskId: String): Task? {
        return Firebase.firestore
            .collection(TASKS_COLLECTION)
            .document(taskId).get().await().toObject()
    }

    override suspend fun updateTask(task: Task) {
        Firebase.firestore
            .collection(TASKS_COLLECTION)
            .document(task.id).set(task).await()
    }

    override suspend fun deleteTask(taskId: String) {
        Firebase.firestore
            .collection(TASKS_COLLECTION)
            .document(taskId).delete().await()
    }

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val TASKS_COLLECTION = "tasks"
    }
}
