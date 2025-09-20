package edu.ucne.RegistroJugadores.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.RegistroJugadores.data.jugadores.local.JugadorDao
import edu.ucne.RegistroJugadores.data.jugadores.local.JugadorEntity
import edu.ucne.RegistroJugadores.data.logros.local.LogroDao
import edu.ucne.RegistroJugadores.data.partidas.local.PartidaDao
import edu.ucne.RegistroJugadores.data.partidas.local.PartidaEntity

@Database(
    entities = [
        JugadorEntity::class,
        PartidaEntity::class
    ],

    version = 5,
    exportSchema = false
)
abstract class JugadorDb : RoomDatabase(){
    abstract fun jugadorDao() : JugadorDao
    abstract fun partidaDao() : PartidaDao
    abstract fun LogroDao() : LogroDao
}
