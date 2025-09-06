package edu.ucne.RegistroJugadores.data.jugadores.repository

import edu.ucne.RegistroJugadores.data.jugadores.local.JugadorDao
import edu.ucne.RegistroJugadores.data.jugadores.mapper.toDomain
import edu.ucne.RegistroJugadores.data.jugadores.mapper.toEntity
import edu.ucne.RegistroJugadores.domain.jugadores.model.Jugador
import edu.ucne.RegistroJugadores.domain.jugadores.repository.JugadorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class JugadorRepositoryImpl @Inject constructor(
    private val dao: JugadorDao
) : JugadorRepository {

    override fun observeJudador(): Flow<List<Jugador>> = dao.ObserveAll().map{
            list -> list.map { it.toDomain() }
    }

    override suspend fun getJugador(id: Int?): Jugador? = dao.getById(id)?.toDomain()

    override suspend fun upsert(jugador: Jugador): Int{
        dao.upsert(jugador.toEntity())
        return jugador.jugadorId
    }

    override suspend fun delete(judador: Jugador) {
        dao.delete(judador.toEntity())
    }

    override suspend fun deleteById(id: Int) {
        dao.deleteById(id)
    }
    override suspend fun existeNombre(nombre: String, excludeId: Int?): Boolean {
        return dao.existeNombre(nombre, excludeId)
    }
}