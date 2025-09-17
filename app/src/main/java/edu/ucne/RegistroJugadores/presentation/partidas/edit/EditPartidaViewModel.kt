package edu.ucne.RegistroJugadores.presentation.partidas.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.RegistroJugadores.domain.jugadores.usecase.ObserveJugadorUseCase
import edu.ucne.RegistroJugadores.domain.partidas.model.Partida
import edu.ucne.RegistroJugadores.domain.partidas.usecase.DeletePartidaUseCase
import edu.ucne.RegistroJugadores.domain.partidas.usecase.GetPartidaUseCase
import edu.ucne.RegistroJugadores.domain.partidas.usecase.UpsertPartidaUseCase
import edu.ucne.RegistroJugadores.domain.partidas.usecase.validateJugador1
import edu.ucne.RegistroJugadores.domain.partidas.usecase.validateJugador2
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPartidaViewModel @Inject constructor(
    private val getPartidaUseCase: GetPartidaUseCase,
    private val upsertPartidaUseCase: UpsertPartidaUseCase,
    private val deletePartidaUseCase: DeletePartidaUseCase,
    private val observeJugadorUseCase: ObserveJugadorUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(value = EditPartidaUiState())

    val state: StateFlow<EditPartidaUiState> = _state.asStateFlow()

    init {
        cargarJugadores()
    }

    fun onEvent(event: EditPartidaUiEvent) {
        when (event) {
            is EditPartidaUiEvent.Load -> onLoad(id = event.id)
            is EditPartidaUiEvent.FechaChanged -> _state.update {
                it.copy(fecha = event.value)
            }
            is EditPartidaUiEvent.Jugador1Changed -> _state.update {
                it.copy(jugador1ID = event.value, jugador1Error = null)
            }

            is EditPartidaUiEvent.Jugador2Changed -> _state.update {
                it.copy(jugador2ID = event.value, jugador2Error = null)
            }

            is EditPartidaUiEvent.GanadorChanged -> _state.update {
                it.copy(ganadorID = event.value)
            }

            is EditPartidaUiEvent.EsFinalizadaChanged -> _state.update {
                it.copy(esFinalizada = event.value)
            }

            is EditPartidaUiEvent.CargarJugadores -> cargarJugadores()

            EditPartidaUiEvent.Save -> onSave()

            EditPartidaUiEvent.Delete -> onDelete()

            EditPartidaUiEvent.Cancel -> _state.update { EditPartidaUiState() }
        }
    }

    private fun onLoad(id: Int?) {
        if (id == null || id == 0) {
            _state.update { it.copy(isNew = true, partidaId = null) }
            return
        }
        viewModelScope.launch {
            val partida = getPartidaUseCase(id)
            if (partida != null) {
                _state.update {
                    it.copy(
                        isNew = false,
                        partidaId = partida.partidaId,
                        fecha = partida.fecha,
                        jugador1ID = partida.jugador1Id,
                        jugador2ID = partida.jugador2Id,
                        ganadorID = partida.ganadorId,
                        esFinalizada = partida.esFinalizada
                    )
                }
            }
        }
    }

    private fun onSave() {
        val jugador1ID = state.value.jugador1ID
        val jugador2ID = state.value.jugador2ID

        val jugador1Validations = validateJugador1(jugador1ID.toString(), jugador2ID.toString())
        val jugador2Validations = validateJugador2(jugador2ID.toString(), jugador1ID.toString())

        if (!jugador1Validations.isValid || !jugador2Validations.isValid) {
            _state.update {
                it.copy(
                    jugador1Error = jugador1Validations.error,
                    jugador2Error = jugador2Validations.error
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }
            val id = state.value.partidaId ?: 0
            val partida = Partida(
                id,
                state.value.fecha,
                state.value.jugador1ID,
                state.value.jugador2ID,
                state.value.ganadorID,
                state.value.esFinalizada
            )
            val result = upsertPartidaUseCase(partida)
            result.onSuccess { newId ->
                _state.update { it.copy(isSaving = false, saved = true) }
            }.onFailure { e ->
                _state.update { it.copy(isSaving = false) }
            }
        }
    }

    private fun onDelete() {
        val id = state.value.partidaId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            deletePartidaUseCase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }

    private fun cargarJugadores() {
        viewModelScope.launch {
            _state.update { it.copy(jugadoresLoading = true) }
            observeJugadorUseCase().collect { jugadores ->
                _state.update { it.copy(jugadoresLoading = false, listaJugadores = jugadores) }
            }
        }
    }
}