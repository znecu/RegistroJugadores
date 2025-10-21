package edu.ucne.RegistroJugadores.presentation.tictactoe

sealed interface TicTacToeUiEvent {
    data class CeldaClick(val fila: Int, val columna: Int) : TicTacToeUiEvent
    data class PartidaIdChange(val value: String) : TicTacToeUiEvent
    data object Refrecar : TicTacToeUiEvent
    data class Mensaje(val message: String?) : TicTacToeUiEvent
}
