package edu.ucne.RegistroJugadores.domain.jugadores.usecase

import edu.ucne.RegistroJugadores.domain.jugadores.model.Jugador
import edu.ucne.RegistroJugadores.domain.jugadores.repository.JugadorRepository

class UpsertJugadorUseCase(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(jugador: Jugador): Result<Int> {
        val nombresResult = validateNombres(jugador.nombres)
        if (!nombresResult.isValid){
            return Result.failure(IllegalArgumentException(nombresResult.error))
        }
        val partidasResult = validatePartidas(jugador.partidas.toString())
        if(!partidasResult.isValid){
            return Result.failure(IllegalArgumentException(partidasResult.error))
        }
        return runCatching { repository.upsert(jugador) }
    }
}