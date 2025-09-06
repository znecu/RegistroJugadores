package edu.ucne.RegistroJugadores.data.jugadores.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.RegistroJugadores.data.jugadores.local.JugadorDao
import edu.ucne.RegistroJugadores.data.jugadores.local.JugadorEntity

@Database(
    entities = [
        JugadorEntity::class
    ],
    version = 2,
    exportSchema = false
)

abstract class JugadorDb : RoomDatabase(){
    abstract fun jugadorDao() : JugadorDao
}
