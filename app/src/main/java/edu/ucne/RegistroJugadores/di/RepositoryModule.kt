package edu.ucne.RegistroJugadores.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.RegistroJugadores.data.jugadores.repository.JugadorRepositoryImpl
import edu.ucne.RegistroJugadores.domain.jugadores.repository.JugadorRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindJugadorRepository(
        jugadorRepositoryImpl: JugadorRepositoryImpl
    ): JugadorRepository
}