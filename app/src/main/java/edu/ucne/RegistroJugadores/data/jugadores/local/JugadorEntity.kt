package edu.ucne.RegistroJugadores.data.jugadores.local
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Jugadores")
class JugadorEntity(
    @PrimaryKey(autoGenerate = true)
    val jugadorId: Int = 0,
    val nombres: String = "",
    val partidas: Int = 0
)