package edu.ucne.RegistroJugadores.partidas.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.type.Date
import edu.ucne.RegistroJugadores.data.jugadores.local.JugadorEntity


@Entity(
    tableName = "Partidas",
    foreignKeys = [
        ForeignKey (
            entity = JugadorEntity::class,
            parentColumns = ["jugadorId"],
            childColumns = ["jugador1ID"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey (
            entity = JugadorEntity::class,
            parentColumns = ["jugadorId"],
            childColumns = ["jugador2ID"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey (
            entity = JugadorEntity::class,
            parentColumns = ["jugadorId"],
            childColumns = ["ganadorID"],
            onDelete = ForeignKey.RESTRICT
        ),
    ]
)

class PartidaEntity(
    @PrimaryKey(autoGenerate = true)
    val partidaId: Int = 0,
    val fecha: Date = Date(),
    val jugador1Id: Int = 0,
    val jugador2Id: Int = 0,
    val ganadorId: Int = 0,
    val esFinalizada: Boolean = false
)