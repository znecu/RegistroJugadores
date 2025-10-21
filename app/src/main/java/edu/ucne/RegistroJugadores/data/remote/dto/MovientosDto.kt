package edu.ucne.RegistroJugadores.data.remote.dto

data class MovientosDto(
    val movimientoId: Int?,
    val jugador: String,
    val posicionFila: Int,
    val posicionColumna: Int
    )
