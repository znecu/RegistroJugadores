package edu.ucne.RegistroJugadores.domain.logros.usecase

import edu.ucne.RegistroJugadores.domain.logros.repository.LogroRepository
import javax.inject.Inject

class DeleteLogroUseCase @Inject constructor(
    private val repository: LogroRepository
) {
    suspend operator fun invoke(id: Int) = repository.deleteById(id)
}

