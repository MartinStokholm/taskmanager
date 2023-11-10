package groupassigment.taskmanager.application.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import groupassigment.taskmanager.application.data.ApiService
import groupassigment.taskmanager.application.data.Repository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TaskManagerModul {
    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService): Repository {
        return Repository(apiService)
    }
}