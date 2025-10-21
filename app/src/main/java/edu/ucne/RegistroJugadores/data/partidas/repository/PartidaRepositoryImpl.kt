package edu.ucne.RegistroJugadores.data.partidas.repository

import edu.ucne.RegistroJugadores.domain.partidas.model.Partida
import edu.ucne.RegistroJugadores.domain.partidas.repository.PartidaRepository
import edu.ucne.RegistroJugadores.data.partidas.local.PartidaDao
import edu.ucne.RegistroJugadores.data.partidas.mapper.toDomain
import edu.ucne.RegistroJugadores.data.partidas.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PartidaRepositoryImpl @Inject constructor(
    private val dao: PartidaDao
): PartidaRepository {
    override fun observePartida(): Flow<List<Partida>> = dao.ObserveAll().map{
            list -> list.map { it.toDomain() }
    }
    override suspend fun getPartida(id: Int?): Partida? = dao.getById(id)?.toDomain()

    override suspend fun upsert(partida: Partida): Int{
        dao.upsert(partida.toEntity())
        return partida.partidaId
    }
    override suspend fun delete(partida: Partida) {
        dao.delete(partida.toEntity())
    }
    override suspend fun deleteById(id: Int) {
        dao.deleteById(id)
    }


}
