package edu.ucne.RegistroJugadores.presentation.partidas.list

import edu.ucne.RegistroJugadores.domain.partidas.model.Partida

data class ListPartidaUiState(
    val isLoading: Boolean = false,
    val partidas: List<Partida> = emptyList(),
    val message: String? = null,
    val navigationToCreate: Boolean = false,
    val navigateToEditId: Int? = null
)
