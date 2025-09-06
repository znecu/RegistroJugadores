package edu.ucne.RegistroJugadores.domain.jugadores.usecase

import edu.ucne.RegistroJugadores.domain.jugadores.repository.JugadorRepository
import javax.inject.Inject

class DeleteJugadorUseCase @Inject constructor(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(id: Int) = repository.deleteById(id)
}