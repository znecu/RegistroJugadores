package edu.ucne.RegistroJugadores.domain.jugadores.model

import java.util.UUID

data class Jugador(
    val id: String = UUID.randomUUID().toString(),
    val remoteId: Int? = null,
    val nombres: String,
    val email: String,
    val isPendingCreate: Boolean = false
)
