package edu.ucne.RegistroJugadores.domain.jugadores.usecase

import edu.ucne.RegistroJugadores.domain.jugadores.model.Jugador
import edu.ucne.RegistroJugadores.domain.jugadores.repository.JugadorRepository
import javax.inject.Inject

class CreateJugadorLocalUseCase @Inject constructor(
    private val repo: JugadorRepository
) {
    suspend operator fun invoke(jugador: Jugador) = repo.createJugadorLocal(jugador)
}