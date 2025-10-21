package edu.ucne.RegistroJugadores.domain.tictactoeapi.model

data class Movimiento(
    val movimientoId: Int,
    val jugador: String,
    val posicionFila: Int,
    val posicionColumna: Int
)
