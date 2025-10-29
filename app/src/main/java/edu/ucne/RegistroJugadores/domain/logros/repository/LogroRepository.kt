package edu.ucne.RegistroJugadores.domain.logros.repository

import edu.ucne.RegistroJugadores.domain.logros.model.Logro
import kotlinx.coroutines.flow.Flow

interface LogroRepository {
    fun observeLogro(): Flow<List<Logro>>

    suspend fun getLogro(id: Int?): Logro?

    suspend fun upsert(logro: Logro): Int

    suspend fun delete(logro: Logro)

    suspend fun deleteById(id: Int)

    suspend fun existeTitulo(titulo: String, excludeId: Int? = null): Boolean
}