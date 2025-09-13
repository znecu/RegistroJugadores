package edu.ucne.RegistroJugadores.domain.partidas.usecase

import edu.ucne.RegistroJugadores.domain.partidas.repository.PartidaRepository
import javax.inject.Inject

class DeletePartidaUseCase @Inject constructor(
    private val repository: PartidaRepository
){
    suspend operator fun invoke(id: Int) = repository.deleteById(id)

}