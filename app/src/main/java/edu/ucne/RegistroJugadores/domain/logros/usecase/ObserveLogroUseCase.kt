package edu.ucne.RegistroJugadores.domain.logros.usecase

import edu.ucne.RegistroJugadores.domain.logros.model.Logro
import edu.ucne.RegistroJugadores.domain.logros.repository.LogroRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveLogroUseCase @Inject constructor(
    private val repository: LogroRepository
){
    operator fun invoke(): Flow<List<Logro>> = repository.observeLogro()

}