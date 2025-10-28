package edu.ucne.RegistroJugadores.data.jugadores.mapper

import edu.ucne.RegistroJugadores.data.jugadores.local.JugadorEntity
import edu.ucne.RegistroJugadores.domain.jugadores.model.Jugador

fun JugadorEntity.toDomain(): Jugador = Jugador(
    id = id,
    nombres = nombres,
    email = email,
    isPendingCreate = isPendingCreate
)

fun Jugador.toEntity(): JugadorEntity = JugadorEntity(
    id = id,
    nombres = nombres,
    email = email,
    isPendingCreate = isPendingCreate
)