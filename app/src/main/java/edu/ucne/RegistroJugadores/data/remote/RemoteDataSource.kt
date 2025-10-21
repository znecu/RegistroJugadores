package edu.ucne.RegistroJugadores.data.remote

import edu.ucne.RegistroJugadores.data.remote.dto.MovientosDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val ticTacToecApi: TicTacToecApi
){
    suspend fun getMovimiento(id: Int) = ticTacToecApi.getMovimiento()
    suspend fun saveMovimientos(movientosDto: MovientosDto) = ticTacToecApi.saveMovimientos(movientosDto)
}