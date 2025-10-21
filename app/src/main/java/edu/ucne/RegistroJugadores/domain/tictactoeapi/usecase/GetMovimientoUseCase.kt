package edu.ucne.RegistroJugadores.domain.tictactoeapi.usecase

import edu.ucne.RegistroJugadores.data.remote.Resource
import edu.ucne.RegistroJugadores.domain.tictactoeapi.model.Movimiento
import edu.ucne.RegistroJugadores.domain.tictactoeapi.repository.MovimientosRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovimientoUseCase @Inject constructor(
    private val repository: MovimientosRepository
) {
    operator fun invoke(id: Int): Flow<Resource<Movimiento>> {
        return repository.getMovimiento(id)
    }
}