package edu.ucne.RegistroJugadores.data.remote.jugadores

import com.squareup.moshi.Json

data class JugadorResponse(
    @Json(name = "jugadorId") val jugadorId: Int?,
    val nombres: String,
    val email: String
)
