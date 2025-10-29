package edu.ucne.RegistroJugadores.presentation.logros.list

import edu.ucne.RegistroJugadores.domain.logros.model.Logro

data class ListLogroUiState (
    val isLoading: Boolean = false,
    val logros: List<Logro> = emptyList(),
    val message: String? = null,
    val navigationToCreate: Boolean = false,
    val navigateToEditId: Int? = null
)