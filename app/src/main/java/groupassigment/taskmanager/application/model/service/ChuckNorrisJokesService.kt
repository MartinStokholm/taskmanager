package groupassigment.taskmanager.application.model.service

import groupassigment.taskmanager.application.model.ChuckNorrisJoke
import retrofit2.http.GET

interface ChuckNorrisJokesService {
    @GET("jokes/random")
    suspend fun getRandomJoke(): ChuckNorrisJoke
}