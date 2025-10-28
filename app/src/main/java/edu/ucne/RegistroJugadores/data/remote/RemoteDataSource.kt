package edu.ucne.RegistroJugadores.data.remote

import edu.ucne.RegistroJugadores.data.remote.dto.MovientosDto
import edu.ucne.RegistroJugadores.data.remote.jugadores.JugadorRequest
import edu.ucne.RegistroJugadores.data.remote.jugadores.JugadorResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val ticTacToeApi: TicTacToeApi
) {

    //movimientos
    suspend fun getMovimiento(id: Int): List<MovientosDto> = ticTacToeApi.getMovimiento(id)
    suspend fun saveMovimientos(movientosDto: MovientosDto) =
        ticTacToeApi.saveMovimientos(movientosDto)

    //jugadores
    suspend fun createJugador(request: JugadorRequest): Resource<JugadorResponse> {
        return try {
            val response = ticTacToeApi.createJugador(request)
            if (response.isSuccessful) {
                response.body()?.let { Resource.Success(it) }
                    ?: Resource.Error("Respuesta vacía del servidor")
            } else {
                Resource.Error("HTTP ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Error de red")
        }
    }

    suspend fun updateJugador(id: Int, request: JugadorRequest): Resource<Unit> {
        return try {
            val response = ticTacToeApi.updateJugador(id, request)
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error("HTTP ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Error de red")
        }
    }

    suspend fun deleteJugador(id: Int): Resource<Unit> {
        return try {
            val response = ticTacToeApi.deleteJugador(id)
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error("HTTP ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Error de red")
        }
    }
}
