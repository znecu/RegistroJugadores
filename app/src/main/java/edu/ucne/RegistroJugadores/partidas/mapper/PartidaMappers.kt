package edu.ucne.RegistroJugadores.partidas.mapper

import edu.ucne.RegistroJugadores.domain.partidas.model.Partida
import edu.ucne.RegistroJugadores.partidas.local.PartidaEntity

fun PartidaEntity.toDomain(): Partida = Partida(
    partidaId = partidaId,
    jugador1Id = jugador1Id,
    jugador2Id = jugador2Id,
    ganadorId = ganadorId
)

fun Partida.toEntity(): PartidaEntity = PartidaEntity(
    partidaId = partidaId,
    jugador1Id = jugador1Id,
    jugador2Id = jugador2Id,
    ganadorId = ganadorId
)