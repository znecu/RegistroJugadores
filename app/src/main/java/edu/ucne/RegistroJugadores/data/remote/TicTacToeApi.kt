package edu.ucne.RegistroJugadores.data.remote

import edu.ucne.RegistroJugadores.data.remote.dto.MovientosDto
import edu.ucne.RegistroJugadores.data.remote.jugadores.JugadorRequest
import edu.ucne.RegistroJugadores.data.remote.jugadores.JugadorResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

//movimientos
interface TicTacToeApi {
    @GET("api/Movimientos/{partidaId}")
    suspend fun getMovimiento(@Path("partidaId") id: Int): List<MovientosDto>
    @POST("api/Movimientos")
    suspend fun saveMovimientos(@Body movientosDto: MovientosDto)

//jugadores
    @POST("api/Jugadores")
    suspend fun createJugador(@Body request: JugadorRequest): Response<JugadorResponse>

    @PUT("api/Jugadores/{id}")
    suspend fun updateJugador(@Path("id") id: Int, @Body request: JugadorRequest): Response<Unit>

    @DELETE("api/Jugadores/{id}")
    suspend fun deleteJugador(@Path("id") id: Int): Response<Unit>

}

