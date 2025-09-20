package edu.ucne.RegistroJugadores.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    object Jugadores : Screen()
    @Serializable
    object Partidas : Screen()
    @Serializable
    object TicTacToe : Screen()
    @Serializable
    object Logros: Screen()
}