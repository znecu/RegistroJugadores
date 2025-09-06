package edu.ucne.RegistroJugadores.domain.jugadores.usecase

import edu.ucne.RegistroJugadores.domain.jugadores.repository.JugadorRepository
import javax.inject.Inject

class ExisteNombreUseCase @Inject constructor(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(nombre: String, excludeId: Int? = null): Boolean {
        return repository.existeNombre(nombre, excludeId)
    }
}