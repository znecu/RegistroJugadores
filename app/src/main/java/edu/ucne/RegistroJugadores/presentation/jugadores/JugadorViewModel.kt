package edu.ucne.RegistroJugadores.presentation.jugadores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.RegistroJugadores.data.remote.Resource
import edu.ucne.RegistroJugadores.domain.jugadores.model.Jugador
import edu.ucne.RegistroJugadores.domain.jugadores.repository.JugadorRepository
import edu.ucne.RegistroJugadores.domain.jugadores.usecase.CreateJugadorLocalUseCase
import edu.ucne.RegistroJugadores.domain.jugadores.usecase.DeleteJugadorUseCase
import edu.ucne.RegistroJugadores.domain.jugadores.usecase.TriggerSyncUseCase
import edu.ucne.RegistroJugadores.domain.jugadores.usecase.UpsertJugadorUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JugadorViewModel @Inject constructor(
    private val createJugadorLocalUseCase: CreateJugadorLocalUseCase,
    private val upsertJugadorUseCase: UpsertJugadorUseCase,
    private val deleteJugadorUseCase: DeleteJugadorUseCase,
    private val triggerSyncUseCase: TriggerSyncUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(JugadorUiState(isLoading = true))
    val state: StateFlow<JugadorUiState> = _state.asStateFlow()

    suspend fun onEvent(event: JugadorEvent) {
        when (event) {
            is JugadorEvent.CrearJugador -> crearJugador(event.nombres, event.email)
            is JugadorEvent.UpdateJugador -> updateJugador(event.jugador)
            is JugadorEvent.DeleteJugador -> deleteJugador(event.id)
            is JugadorEvent.ShowCreateSheet -> _state.update { it.copy(showCreateSheet = true) }
            is JugadorEvent.HideCreateSheet -> _state.update {
                it.copy(
                    showCreateSheet = false,
                    jugadorNombres = "",
                    jugadorEmail = ""
                )
            }

            is JugadorEvent.OnNombresChange -> _state.update { it.copy(jugadorNombres = event.nombres) }
            is JugadorEvent.OnEmailChange -> _state.update { it.copy(jugadorEmail = event.email) }
            is JugadorEvent.UserMessageShown -> _state.update { it.copy(userMessage = null) }
        }
    }

    private fun crearJugador(nombre: String, email: String) = viewModelScope.launch {
        val jugador = Jugador(nombres = nombre, email = email)
        when (val result = createJugadorLocalUseCase(jugador)) {
            is Resource.Success -> {
                _state.update {
                    it.copy(
                        userMessage = "Jugador creado localmente",
                        showCreateSheet = false,
                        jugadorNombres = "",
                        jugadorEmail = ""
                    )
                }
                triggerSyncUseCase()
            }

            is Resource.Error -> _state.update { it.copy(userMessage = result.message) }
            else -> {}
        }
    }

    private fun updateJugador(jugador: Jugador) = viewModelScope.launch {
        when (val result = upsertJugadorUseCase(jugador)) {
            is Resource.Success -> _state.update { it.copy(userMessage = "Jugador actualizado") }
            is Resource.Error -> _state.update { it.copy(userMessage = result.message) }
            else -> {}
        }
    }

    private fun deleteJugador(id: String) = viewModelScope.launch {
        when (val result = deleteJugadorUseCase(id)) {
            is Resource.Success -> _state.update { it.copy(userMessage = "Jugador eliminado") }
            is Resource.Error -> _state.update { it.copy(userMessage = result.message) }
            else -> {}
        }
    }

    private fun clearMessage() {
        _state.update { it.copy(userMessage = null) }
    }
}