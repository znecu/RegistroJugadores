package edu.ucne.RegistroJugadores.data.remote.repository

import edu.ucne.RegistroJugadores.data.remote.RemoteDataSource
import edu.ucne.RegistroJugadores.data.remote.Resource
import edu.ucne.RegistroJugadores.data.remote.mapper.toDomain
import edu.ucne.RegistroJugadores.data.remote.mapper.toDto
import edu.ucne.RegistroJugadores.domain.tictactoeapi.model.Movimiento
import edu.ucne.RegistroJugadores.domain.tictactoeapi.repository.MovimientosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovimientosRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : MovimientosRepository {

    override fun getMovimiento(id: Int): Flow<Resource<Movimiento>> = flow {
        try {
            emit(Resource.Loading<Movimiento>())

            val movimientosDto = remoteDataSource.getMovimiento(id)

            if (movimientosDto.isNotEmpty()) {
                val movimiento = movimientosDto.first().toDomain()
                emit(Resource.Success(movimiento))
            } else {
                emit(Resource.Error("No se encontraron movimientos"))
            }

        } catch (e: HttpException) {
            emit(Resource.Error("Error de servidor: ${e.message()}"))
        } catch (e: IOException) {
            emit(Resource.Error("Error de red: ${e.message}"))
        } catch (e: Exception) {
            emit(Resource.Error("Error desconocido: ${e.localizedMessage}"))
        }
    }

    override suspend fun saveMovimiento(movimiento: Movimiento): Resource<Unit> {
        return try {
            val movimientoDto = movimiento.toDto()
            remoteDataSource.saveMovimientos(movimientoDto)
            Resource.Success(Unit)
        } catch (e: HttpException) {
            Resource.Error("Error de servidor: ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("Error de red: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Error desconocido: ${e.localizedMessage}")
        }
    }
}