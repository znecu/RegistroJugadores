package edu.ucne.RegistroJugadores.data.jugadores.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.RegistroJugadores.data.remote.Resource
import kotlinx.coroutines.flow.Flow

@Dao
interface JugadorDao {
    @Query(value = "SELECT * FROM Jugadores ORDER BY id DESC")
    fun ObserveAll(): Flow<List<JugadorEntity>>

    @Query(value = "SELECT * FROM Jugadores WHERE id = :id")
    suspend fun getById(id: Int?): JugadorEntity?

    @Query(value = "SELECT * FROM Jugadores WHERE id = :id")
    suspend fun getById(id: String): JugadorEntity?

    @Upsert
    suspend fun upsert(entity: JugadorEntity)

    @Delete
    suspend fun delete(entity: JugadorEntity)

    @Query(value = "DELETE FROM Jugadores WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM Jugadores WHERE LOWER(TRIM(nombres)) = LOWER(TRIM(:nombre)) AND (:excludeId IS NULL OR id != :excludeId))")
    suspend fun existeNombre(nombre: String, excludeId: Int?): Boolean

    @Query("SELECT * FROM Jugadores WHERE isPendingCreate = 1")
    suspend fun getPendingCreateJugadores(): List<JugadorEntity>

    @Query("DELETE FROM Jugadores WHERE id = :id")
    suspend fun deleteById(id: String)
}