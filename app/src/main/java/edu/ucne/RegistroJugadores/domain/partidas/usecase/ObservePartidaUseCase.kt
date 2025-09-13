package edu.ucne.RegistroJugadores.domain.partidas.usecase

import edu.ucne.RegistroJugadores.domain.partidas.model.Partida
import edu.ucne.RegistroJugadores.domain.partidas.repository.PartidaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObservePartidaUseCase @Inject constructor(
    private val repository : PartidaRepository
){
    operator fun invoke(): Flow<List<Partida>> = repository.observePartida()
}