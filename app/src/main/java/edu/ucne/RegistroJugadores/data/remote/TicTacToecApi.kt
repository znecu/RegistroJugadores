package edu.ucne.RegistroJugadores.data.remote

import edu.ucne.RegistroJugadores.data.remote.dto.MovientosDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TicTacToecApi {
    @GET("api/Movimientos/{partidaId}")
    suspend fun getMovimiento(): List<MovientosDto>

    @POST("api/Movimientos")
    suspend fun saveMovimientos(@Body movientosDto: MovientosDto): Response<Unit>
}