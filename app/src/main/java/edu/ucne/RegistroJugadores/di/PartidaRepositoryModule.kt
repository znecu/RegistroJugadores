package edu.ucne.RegistroJugadores.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.RegistroJugadores.data.partidas.repository.PartidaRepositoryImpl
import edu.ucne.RegistroJugadores.domain.partidas.repository.PartidaRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PartidaRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindPartidaRepository(
        partidaRepositoryImpl: PartidaRepositoryImpl
    ): PartidaRepository
}
