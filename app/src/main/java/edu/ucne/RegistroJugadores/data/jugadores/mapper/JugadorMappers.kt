package edu.ucne.RegistroJugadores.data.jugadores.mapper

import edu.ucne.RegistroJugadores.data.jugadores.local.JugadorEntity
import edu.ucne.RegistroJugadores.domain.jugadores.model.Jugador

fun JugadorEntity.toDomain(): Jugador = Jugador(
    jugadorId = jugadorId,
    nombres = nombres,
    partidas = partidas
)

fun Jugador.toEntity(): JugadorEntity = JugadorEntity(
    jugadorId = jugadorId,
    nombres = nombres,
    partidas = partidas
)