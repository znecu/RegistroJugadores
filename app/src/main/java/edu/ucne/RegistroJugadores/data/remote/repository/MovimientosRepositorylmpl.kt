package edu.ucne.RegistroJugadores.data.remote.repository

import edu.ucne.RegistroJugadores.data.remote.RemoteDataSource
import edu.ucne.RegistroJugadores.data.remote.Resource
import edu.ucne.RegistroJugadores.data.remote.dto.MovientosDto
import edu.ucne.RegistroJugadores.data.remote.mapper.toDomain
import edu.ucne.RegistroJugadores.domain.tictactoeapi.model.Movimiento
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class MovimientosRepositorylmpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val movientosDto: MovientosDto
) {
    fun getMovimiento(id: Int): Flow<Resource<List<Movimiento>>> = flow {
        try {
            emit(Resource.Loading())
            val movimiento = remoteDataSource.getMovimiento(id).map { it.toDomain() }
            emit(Resource.Success(movimiento))
        } catch (e: HttpException) {
            emit(Resource.Error("Error de desconocido: ${e.message()}"))
        }
    }
    suspend fun saveMovimiento(movimiento: Movimiento) = remoteDataSource.saveMovimientos(movientosDto)

}