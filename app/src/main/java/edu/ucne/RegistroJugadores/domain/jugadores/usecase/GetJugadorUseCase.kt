package edu.ucne.RegistroJugadores.domain.jugadores.usecase

import edu.ucne.RegistroJugadores.domain.jugadores.model.Jugador
import edu.ucne.RegistroJugadores.domain.jugadores.repository.JugadorRepository
import javax.inject.Inject

class GetJugadorUseCase @Inject constructor(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(id: Int?): Jugador? = repository.getJugador(id)
}