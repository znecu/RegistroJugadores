package edu.ucne.RegistroJugadores.domain.partidas.model
import java.util.Date

data class Partida(
    val partidaId: Int = 0,
    val fecha: Date = Date(),
    val jugador1Id: Int = 0,
    val jugador2Id: Int = 0,
    val ganadorId: Int = 0,
    val esFinalizada: Boolean = false
)
