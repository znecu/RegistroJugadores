package edu.ucne.RegistroJugadores.presentation.tictactoe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.RegistroJugadores.data.remote.Resource
import edu.ucne.RegistroJugadores.domain.tictactoeapi.model.Movimiento
import edu.ucne.RegistroJugadores.domain.tictactoeapi.repository.MovimientosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicTacToeViewModel @Inject constructor(
    private val movimientosRepository: MovimientosRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TicTacToeUiState())
    val state: StateFlow<TicTacToeUiState> = _state.asStateFlow()

    fun onEvent(event: TicTacToeUiEvent) {
        when (event) {
            is TicTacToeUiEvent.CeldaClick -> registrarMovimiento(event.fila, event.columna)
            is TicTacToeUiEvent.PartidaIdChange -> _state.update { it.copy(partidaId = event.value) }
            is TicTacToeUiEvent.Refrecar -> refrescarMovimientos()
            is TicTacToeUiEvent.Mensaje -> _state.update { it.copy(message = event.message) }
        }
    }

    private fun refrescarMovimientos() {
        val partidaIdInt = _state.value.partidaId.toIntOrNull()
        if (partidaIdInt == null) {
            _state.update { it.copy(error = "ID de partida inválido") }
            return
        }

        _state.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            movimientosRepository.getMovimiento(partidaIdInt).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                    is Resource.Success -> {
                        val movimientos = resource.data ?: emptyList()
                        val nuevoTablero = crearTableroDesdeMovimientos(movimientos)
                        _state.update {
                            it.copy(
                                movimientos = movimientos,
                                tablero = nuevoTablero,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                    is Resource.Error -> _state.update {
                        it.copy(
                            isLoading = false,
                            error = resource.message ?: "Error al cargar movimientos"
                        )
                    }
                }
            }
        }
    }
    private fun registrarMovimiento(fila: Int, columna: Int) {
        val partidaIdInt = _state.value.partidaId.toIntOrNull()
        if (partidaIdInt == null) {
            _state.update { it.copy(error = "ID de partida inválido") }
            return
        }

        if (_state.value.tablero[fila][columna].isNotEmpty()) {
            _state.update { it.copy(message = "Esta celda ya está ocupada") }
            return
        }

        val nuevoMovimiento = Movimiento(
            partidaId = partidaIdInt,
            jugador = _state.value.turnoActual,
            posicionFila = fila,
            posicionColumna = columna
        )

        viewModelScope.launch {
            when (val result: Resource<Unit> = movimientosRepository.saveMovimiento(nuevoMovimiento)) {
                is Resource.Success<Unit> -> {
                    val nuevoTablero = _state.value.tablero.map { it.toMutableList() }.toMutableList()
                    nuevoTablero[fila][columna] = _state.value.turnoActual

                    val siguienteTurno = if (_state.value.turnoActual == "X") "O" else "X"

                    _state.update {
                        it.copy(
                            tablero = nuevoTablero,
                            turnoActual = siguienteTurno,
                            message = "Movimiento realizado exitosamente"
                        )
                    }

                    refrescarMovimientos()
                }

                is Resource.Error<Unit> -> {
                    _state.update {
                        it.copy(error = result.message ?: "Error al registrar movimiento")
                    }
                }

                is Resource.Loading<Unit> -> {
                    _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private fun crearTableroDesdeMovimientos(movimientos: List<Movimiento>): List<List<String>> {
        val tablero = MutableList(3) { MutableList(3) { "" } }

        movimientos.forEach { mov ->
            if (mov.posicionFila in 0..2 && mov.posicionColumna in 0..2) {
                tablero[mov.posicionFila][mov.posicionColumna] = mov.jugador
            }
        }

        return tablero
    }
}