package edu.ucne.RegistroJugadores.presentation.partidas.edit

data class EditPartidaUiState(
    val partidaId: Int? = null,
    val jugador1ID: Int = 0,
    val jugador2ID: Int = 0,
    val fecha: String = "",
    val ganadorID: Int? = null,
    val esFinalizada: Boolean = false,
    val jugador1Error: String? = null,
    val jugador2Error: String? = null,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = false,
    val deleted: Boolean = false
)
