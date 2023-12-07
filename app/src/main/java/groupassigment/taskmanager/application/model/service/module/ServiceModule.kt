package groupassigment.taskmanager.application.model.service.module

import groupassigment.taskmanager.application.model.service.AccountService
import groupassigment.taskmanager.application.model.service.StorageService
import groupassigment.taskmanager.application.model.service.impl.AccountServiceImpl
import groupassigment.taskmanager.application.model.service.impl.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import groupassigment.taskmanager.application.model.service.ChuckNorrisJokesService
import groupassigment.taskmanager.application.model.service.impl.ChuckNorrisJokesServiceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

  @Binds abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

  @Binds abstract fun provideChuckNorrisJokesService(impl: ChuckNorrisJokesServiceImpl): ChuckNorrisJokesService

  @Binds abstract fun provideStorageService(impl: StorageServiceImpl): StorageService
}
