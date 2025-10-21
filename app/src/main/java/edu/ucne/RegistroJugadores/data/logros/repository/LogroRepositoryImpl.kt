package edu.ucne.RegistroJugadores.data.logros.repository

import edu.ucne.RegistroJugadores.data.logros.local.LogroDao
import edu.ucne.RegistroJugadores.data.logros.mapper.toDomain
import edu.ucne.RegistroJugadores.data.logros.mapper.toEntity
import edu.ucne.RegistroJugadores.domain.logros.model.Logro
import edu.ucne.RegistroJugadores.domain.logros.repository.LogroRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LogroRepositoryImpl @Inject constructor(
    private val dao: LogroDao
): LogroRepository {

    override fun observeLogro(): Flow<List<Logro>> = dao.ObserveAll().map{
            list -> list.map { it.toDomain() }
    }
    override suspend fun getLogro(id: Int?): Logro? = dao.getById(id)?.toDomain()

    override suspend fun upsert(logro: Logro): Int{
        dao.upsert(logro.toEntity())
        return logro.logroId
    }

    override suspend fun delete(logro: Logro) {
        dao.delete(logro.toEntity())
    }

    override suspend fun deleteById(id: Int){
        dao.deleteById(id)
    }

    override suspend fun existeTitulo(titulo: String, excludeId: Int?): Boolean {
        return dao.existeTitulo(titulo, excludeId)
    }
}