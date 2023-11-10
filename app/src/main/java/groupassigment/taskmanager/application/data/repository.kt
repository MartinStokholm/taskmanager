package groupassigment.taskmanager.application.data

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Inject

interface ApiService {

}

class Repository @Inject constructor(
    private val apiService: ApiService
){

}