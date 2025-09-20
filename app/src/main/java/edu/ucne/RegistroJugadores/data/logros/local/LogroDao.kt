package edu.ucne.RegistroJugadores.data.logros.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface LogroDao {
    @Query(value = "SELECT * FROM Logros ORDER BY logroId DESC")
    fun ObserveAll(): Flow<List<LogroEntity>>

    @Query(value = "SELECT * FROM Logros Where logroId = :id")
    suspend fun  getById(id: Int?): LogroEntity?

    @Upsert
    suspend fun upsert(entity: LogroEntity)

    @Delete
    suspend fun delete(entity: LogroEntity)

    @Query(value = "DELETE FROM logros WHERE logroId = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM Logros WHERE LOWER(TRIM(titulo)) = LOWER(TRIM(:titulo)) AND (:excludeId IS NULL OR logroId != :excludeId))")
    suspend fun existeTitulo(titulo: String, excludeId: Int?): Boolean

}