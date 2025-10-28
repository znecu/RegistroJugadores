package edu.ucne.RegistroJugadores.presentation.jugadores

import edu.ucne.RegistroJugadores.domain.jugadores.model.Jugador

sealed interface JugadorEvent {
    data class CrearJugador(val nombres: String) : JugadorEvent
    data class UpdateJugador(val jugador: Jugador) : JugadorEvent
    data class DeleteJugador(val id: String) : JugadorEvent
    object ShowCreateSheet : JugadorEvent
    object HideCreateSheet : JugadorEvent
    data class OnNombresChange(val nombres: String) : JugadorEvent
    data class OnEmailChange(val email: String) : JugadorEvent
    object UserMessageShown: JugadorEvent
}