package edu.ucne.RegistroJugadores.domain.jugadores.repository

import edu.ucne.RegistroJugadores.data.jugadores.local.JugadorEntity
import edu.ucne.RegistroJugadores.domain.jugadores.model.Jugador
import kotlinx.coroutines.flow.Flow

interface JugadorRepository {
    fun observeJudador(): Flow<List<Jugador>>

    suspend fun getJugador(id: Int) : Jugador?

    suspend fun upsert(jugador: Jugador): Int

    suspend fun delete(jugador: Jugador)

    suspend fun deleteById(id: Int)

}