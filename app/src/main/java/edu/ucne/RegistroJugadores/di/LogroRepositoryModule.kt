package edu.ucne.RegistroJugadores.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.RegistroJugadores.data.logros.repository.LogroRepositoryImpl
import edu.ucne.RegistroJugadores.domain.logros.repository.LogroRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LogroRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindLogroRepository(
        logroRepositoryImpl: LogroRepositoryImpl
    ): LogroRepository
}
