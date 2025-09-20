package edu.ucne.RegistroJugadores.domain.logros.usecase

import edu.ucne.RegistroJugadores.domain.logros.model.Logro
import edu.ucne.RegistroJugadores.domain.logros.repository.LogroRepository
import javax.inject.Inject

class GetLogroUseCase @Inject constructor(
    private val repository: LogroRepository
) {
    suspend operator fun invoke(id: Int?): Logro? = repository.getLogro(id)

}