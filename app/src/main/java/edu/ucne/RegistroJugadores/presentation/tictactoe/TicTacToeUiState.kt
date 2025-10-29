package edu.ucne.RegistroJugadores.presentation.tictactoe

import edu.ucne.RegistroJugadores.domain.tictactoeapi.model.Movimiento

data class TicTacToeUiState(
    val partidaId: String = "",
    val tablero: List<List<String>> = List(3) { List(3) { "" } },
    val movimientos: List<Movimiento> = emptyList(),
    val turnoActual: String = "X",
    val isLoading: Boolean = false,
    val message: String? = null,
    val error: String? = null
)
