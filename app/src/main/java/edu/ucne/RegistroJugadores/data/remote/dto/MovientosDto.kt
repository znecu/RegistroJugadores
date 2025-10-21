package edu.ucne.RegistroJugadores.data.remote.dto

data class MovientosDto(
    val partidaId: Int?,
    val jugador: String,
    val posicionFila: Int,
    val posicionColumna: Int
    )
