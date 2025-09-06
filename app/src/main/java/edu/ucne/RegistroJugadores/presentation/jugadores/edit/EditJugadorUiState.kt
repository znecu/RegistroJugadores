package edu.ucne.RegistroJugadores.presentation.jugadores.edit

data class EditJugadorUiState(
    val jugadorId: Int? = null,
    val nombres: String = "",
    val partidas: String = "",
    val nombreError: String? = null,
    val partidasError: String? = null,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = false,
    val deleted: Boolean = false
)
