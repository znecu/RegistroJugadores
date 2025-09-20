package edu.ucne.RegistroJugadores.domain.logros.usecase

import edu.ucne.RegistroJugadores.domain.logros.model.Logro
import edu.ucne.RegistroJugadores.domain.logros.repository.LogroRepository
import javax.inject.Inject

class UpsertLogroUseCase @Inject constructor(
    private val repository: LogroRepository
){
    suspend operator fun invoke(logro: Logro): Result<Int> {
        val tituloResult = validateTitulo(logro.titulo)
        if (!tituloResult.isValid){
            return Result.failure(IllegalArgumentException(tituloResult.error))
        }
        val descripcionResult = validateDescripcion(logro.descripcion)
        if(!descripcionResult.isValid){
            return Result.failure(IllegalArgumentException(descripcionResult.error))
        }
        return runCatching { repository.upsert(logro) }

    }
}