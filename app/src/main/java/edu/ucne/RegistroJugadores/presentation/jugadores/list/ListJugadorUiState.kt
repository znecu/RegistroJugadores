package edu.ucne.RegistroJugadores.presentation.jugadores.list

import edu.ucne.RegistroJugadores.domain.jugadores.model.Jugador

data class ListJugadorUiState(
    val isLoading: Boolean = false,
    val jugadores: List<Jugador> = emptyList(),
    val message: String? = null,
    val navigationToCreate: Boolean = false,
    val navigateToEditId: Int? = null
)
