package edu.ucne.RegistroJugadores.domain.jugadores.repository

import edu.ucne.RegistroJugadores.data.remote.Resource
import edu.ucne.RegistroJugadores.domain.jugadores.model.Jugador
import kotlinx.coroutines.flow.Flow

interface JugadorRepository {
    fun observeJudador(): Flow<List<Jugador>>

    suspend fun getJugador(id: Int?) : Jugador?

//    suspend fun upsert(jugador: Jugador): Int

    suspend fun delete(jugador: Jugador): Resource<Unit>

    suspend fun deleteById(id: Int)

    suspend fun existeNombre(nombre: String, excludeId: Int? = null): Boolean

    //--
    suspend fun getJugador(id: String): Jugador?
    suspend fun createJugadorLocal(jugador: Jugador): Resource<Jugador>
    suspend fun upsert(jugador: Jugador): Resource<Unit>
    suspend fun delete(id: String): Resource<Unit>
    suspend fun postPendingJugadores(): Resource<Unit>





}