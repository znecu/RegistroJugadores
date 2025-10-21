package edu.ucne.RegistroJugadores.data.remote

import edu.ucne.RegistroJugadores.data.remote.dto.MovientosDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val ticTacToeApi: TicTacToeApi
){
    suspend fun getMovimiento(id: Int): List<MovientosDto> = ticTacToeApi.getMovimiento(id)
    suspend fun saveMovimientos(movientosDto: MovientosDto) = ticTacToeApi.saveMovimientos(movientosDto)

}