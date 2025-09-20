package edu.ucne.RegistroJugadores.data.logros.mapper

import edu.ucne.RegistroJugadores.data.logros.local.LogroEntity
import edu.ucne.RegistroJugadores.domain.logros.model.Logro

fun LogroEntity.toDomain(): Logro = Logro(
    logroId = logroId,
    titulo = titulo,
    descripcion = descripcion
)
fun Logro.toEntity(): LogroEntity = LogroEntity(
    logroId = logroId,
    titulo = titulo,
    descripcion = descripcion
)