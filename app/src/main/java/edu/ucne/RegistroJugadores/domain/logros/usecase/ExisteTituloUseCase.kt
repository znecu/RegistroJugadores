package edu.ucne.RegistroJugadores.domain.logros.usecase

import edu.ucne.RegistroJugadores.domain.logros.repository.LogroRepository
import javax.inject.Inject

class ExisteTituloUseCase @Inject constructor(
    private val repository: LogroRepository
){
    suspend operator fun invoke(titulo: String, excludeId: Int? = null): Boolean {
     return repository.existeTitulo(titulo, excludeId)
    }
}