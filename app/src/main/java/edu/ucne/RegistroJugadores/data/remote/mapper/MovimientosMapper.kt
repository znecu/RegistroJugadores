package edu.ucne.RegistroJugadores.data.remote.mapper

import edu.ucne.RegistroJugadores.data.remote.dto.MovientosDto
import edu.ucne.RegistroJugadores.domain.tictactoeapi.model.Movimiento

fun MovientosDto.toDomain(): Movimiento = Movimiento(
    partidaId = movimientoId,
    jugador = jugador,
    posicionFila = posicionFila,
    posicionColumna = posicionColumna
)
fun Movimiento.toDto(): MovientosDto = MovientosDto(
    movimientoId = partidaId,
    jugador = jugador,
    posicionFila = posicionFila,
    posicionColumna = posicionColumna
)