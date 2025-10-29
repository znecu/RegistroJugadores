package edu.ucne.RegistroJugadores.presentation.jugadores

import edu.ucne.RegistroJugadores.domain.jugadores.model.Jugador

data class JugadorUiState(
    val isLoading: Boolean = false,
    val jugadores: List<Jugador> = emptyList(),
    val userMessage: String? = null,
    val showCreateSheet: Boolean = false,
    val jugadorNombres: String = "",
    val jugadorEmail: String = ""
)
