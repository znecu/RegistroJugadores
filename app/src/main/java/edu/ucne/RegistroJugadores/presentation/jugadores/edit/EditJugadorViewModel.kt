package edu.ucne.RegistroJugadores.presentation.jugadores.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.RegistroJugadores.domain.jugadores.model.Jugador
import edu.ucne.RegistroJugadores.domain.jugadores.usecase.DeleteJugadorUseCase
import edu.ucne.RegistroJugadores.domain.jugadores.usecase.GetJugadorUseCase
import edu.ucne.RegistroJugadores.domain.jugadores.usecase.UpsertJugadorUseCase
import edu.ucne.RegistroJugadores.domain.jugadores.usecase.ExisteNombreUseCase
import edu.ucne.RegistroJugadores.domain.jugadores.usecase.validateNombres
import edu.ucne.RegistroJugadores.domain.jugadores.usecase.validatePartidas
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditJugadorViewModel @Inject constructor(
    private val getJugadorUseCase: GetJugadorUseCase,
    private val upsertJugadorUseCase: UpsertJugadorUseCase,
    private val deleteJugadorUseCase: DeleteJugadorUseCase,
    private val existeNombreUseCase: ExisteNombreUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(value = EditJugadorUiState())

    val state: StateFlow<EditJugadorUiState> = _state.asStateFlow()

    fun onEvent(event: EditJugadorUiEvent) {
        when (event) {
            is EditJugadorUiEvent.Load -> onLoad(id = event.id)
            is EditJugadorUiEvent.NombresChanged -> _state.update {
                it.copy(nombres = event.value, nombreError = null)
            }

            is EditJugadorUiEvent.PartidasChanged -> _state.update {
                it.copy(partidas = event.value, partidasError = null)
            }

            EditJugadorUiEvent.Save -> onSave()
            EditJugadorUiEvent.Delete -> onDelete()
        }
    }

    private fun onLoad(id: Int?) {
        if (id == null || id == 0) {
            _state.update { it.copy(isNew = true, jugadorId = null) }
            return
        }
        viewModelScope.launch {
            val jugador = getJugadorUseCase(id)
            if (jugador != null) {
                _state.update {
                    it.copy(
                        isNew = false,
                        jugadorId = jugador.jugadorId,
                        nombres = jugador.nombres,
                        partidas = jugador.partidas.toString()
                    )
                }
            }
        }
    }

    private fun onSave() {
        val nombres = state.value.nombres
        val nombresValidations = validateNombres(nombres)
        val partidas = state.value.partidas
        val p = validatePartidas(partidas)

        if (!nombresValidations.isValid || !p.isValid) {
            _state.update {
                it.copy(
                    nombreError = nombresValidations.error,
                    partidasError = p.error
                )
            }
            return
        }

        viewModelScope.launch {
            val currentId = state.value.jugadorId
            if (existeNombreUseCase(nombres, currentId)) {
                _state.update {
                    it.copy(
                        nombreError = "Ya existe un jugador con ese nombre"
                    )
                }
                return@launch
            }

            _state.update { it.copy(isSaving = true) }
            val id = state.value.jugadorId ?: 0
            val jugador = Jugador(
                jugadorId = id,
                nombres = nombres,
                partidas = partidas.toInt()
            )
            val result = upsertJugadorUseCase(jugador)
            result.onSuccess { newId ->
                _state.value = EditJugadorUiState()
            }.onFailure { e ->
                _state.update { it.copy(isSaving = false) }
            }
        }
    }

    private fun onDelete() {
        val id = state.value.jugadorId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            deleteJugadorUseCase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }
}