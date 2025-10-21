package edu.ucne.RegistroJugadores.data.remote.mapper

import edu.ucne.RegistroJugadores.data.remote.dto.MovientosDto
import edu.ucne.RegistroJugadores.domain.tictactoeapi.model.Movimiento

fun MovientosDto.toDomain(): Movimiento = Movimiento(
    movimientoId = movimientoId,
    jugador = jugador,
    posicionFila = posicionFila,
    posicionColumna = posicionColumna
)
fun Movimiento.toDto(): MovientosDto = MovientosDto(
    movimientoId = movimientoId,
    jugador = jugador,
    posicionFila = posicionFila,
    posicionColumna = posicionColumna
)