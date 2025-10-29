package edu.ucne.RegistroJugadores.data.logros.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Logros")
class LogroEntity(
    @PrimaryKey(autoGenerate = true)
    val logroId: Int = 0,
    val titulo: String = "",
    val descripcion: String = "",

)