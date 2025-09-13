package edu.ucne.RegistroJugadores.domain.partidas.usecase

import edu.ucne.RegistroJugadores.domain.partidas.model.Partida
import edu.ucne.RegistroJugadores.domain.partidas.repository.PartidaRepository
import javax.inject.Inject

class GetPartidaUseCase @Inject constructor(
    private val repository: PartidaRepository
){
    suspend operator fun invoke(id: Int?): Partida? = repository.getPartida(id)
}

