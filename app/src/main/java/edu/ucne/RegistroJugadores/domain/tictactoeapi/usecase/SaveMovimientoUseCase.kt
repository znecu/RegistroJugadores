package edu.ucne.RegistroJugadores.domain.tictactoeapi.usecase

import edu.ucne.RegistroJugadores.domain.tictactoeapi.model.Movimiento
import edu.ucne.RegistroJugadores.domain.tictactoeapi.repository.MovimientosRepository
import javax.inject.Inject

class SaveMovimientoUseCase @Inject constructor(
    private val repository: MovimientosRepository
) {
    suspend operator fun invoke(movimiento: Movimiento) = repository.saveMovimiento(movimiento)
}