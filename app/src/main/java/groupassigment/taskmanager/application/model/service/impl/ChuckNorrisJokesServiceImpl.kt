package groupassigment.taskmanager.application.model.service.impl

import groupassigment.taskmanager.application.model.ChuckNorrisJoke
import groupassigment.taskmanager.application.model.service.ChuckNorrisJokesService
import retrofit2.Retrofit
import javax.inject.Inject

class ChuckNorrisJokesServiceImpl  @Inject constructor(retrofit: Retrofit) :
    ChuckNorrisJokesService {
private val chuckNorrisApi = retrofit.create(ChuckNorrisJokesService::class.java)
    override suspend fun getRandomJoke(): ChuckNorrisJoke {
        return chuckNorrisApi.getRandomJoke()
    }
}


