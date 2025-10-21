package edu.ucne.RegistroJugadores.domain.partidas.repository

import edu.ucne.RegistroJugadores.domain.partidas.model.Partida
import kotlinx.coroutines.flow.Flow

interface PartidaRepository {
    fun observePartida(): Flow<List<Partida>>
    suspend fun getPartida(id: Int?): Partida?
    suspend fun upsert(partida: Partida): Int
    suspend fun delete(partida: Partida)
    suspend fun deleteById(id: Int)

}