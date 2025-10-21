package edu.ucne.RegistroJugadores.domain.tictactoeapi.repository

import com.google.api.ResourceReference
import edu.ucne.RegistroJugadores.data.remote.Resource
import edu.ucne.RegistroJugadores.domain.tictactoeapi.model.Movimiento
import kotlinx.coroutines.flow.Flow

interface MovimientosRepository {

    fun getMovimiento(id: Int): Flow<Resource<Movimiento>>
    suspend fun saveMovimiento(movimiento: Movimiento): Resource<Unit>
}